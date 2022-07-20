package com.action;

import com.domain.PageInfo;
import com.domain.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import com.sun.deploy.net.HttpRequest;
import mymvc.ModelAndView;
import mymvc.RequestMapping;
import mymvc.RequestParam;
import mymvc.ResponseBody;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAction {

    private UserService service = UserServiceImpl.getService() ;
    @RequestMapping("login.do")
    public String login(@RequestParam("uname") String uname , @RequestParam("upass") String upass, HttpServletRequest req) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        User user = service.checkLogin(uname,upass) ;
        if(user == null){
            //登录失败
            return "redirect:index.jsp?flag=9" ;
        }else{
            //登录成功
            //登录成功的信息装入session缓存
            req.getSession().setAttribute("loginUser",user);
            return "redirect:main.jsp" ;
        }
    }

    //用户点击搜索提供条件过滤
    @RequestMapping("userList.do")
    public ModelAndView userList(@RequestParam("uno") Integer uno, @RequestParam("uname") String uname , @RequestParam("sex")  String sex , @RequestParam("page") Integer page , @RequestParam("row") Integer row){
        //把传过来的数据存入集合里面传过去
        ModelAndView mv = new ModelAndView();
        Map<String,Object> ma = new HashMap<>();
        if(page == null){
            //没有传递page ， 应该是菜单访问，默认访问第1页
            page = 1 ;
            row = 5 ;
        }
        ma.put("uno",uno);//第一次为""
        ma.put("uname",uname);//第一次为""
        ma.put("sex",sex);//第一次为""
        ma.put("page",page);//第一次为1
        ma.put("row",row);//
        PageInfo pageInfo = service.findUserByPageAndFilter(ma);
        mv.setViewName("userList.jsp");
        mv.addAttribute("sex",sex);
        mv.addAttribute("uno",uno);
        mv.addAttribute("uname",uname);
        mv.addAttribute("page",page);
        mv.addAttribute("row",row);
        mv.addAttribute("pageInfo",pageInfo);
        return mv;
    }


    @RequestMapping("addUser.do")
    @ResponseBody
    public String addUser(User user){
        service.saveUser(user);
        return "保存成功";
    }

    //删除事件
    @RequestMapping("deleteUser.do")
    public String del(@RequestParam("uno") String uno){
        int id = Integer.parseInt(uno);
        service.del(id);
        //删除完了以后需要更新
        //使用重定向的好处：1.把过滤的参数去掉 2.直接请求.do和.jsp的区别 一个会加载数据一个不会
        return "redirect:userList.do";
    }
    //删除多个
    @RequestMapping("delects.do")
    public String dels(@RequestParam("arr") String arr){
        service.dels(arr);
        return "redirect:userList.do";
    }



    //处理编辑
    @RequestMapping("edit.do")
    public ModelAndView edit(@RequestParam("uno") Integer uno){
        User user = service.selectOne(uno);
        ModelAndView ma = new ModelAndView();
        ma.setViewName("edit.jsp");
        ma.addAttribute("user",user);
        System.out.println("user:"+user);
        return ma;
    }
    //修改单个数据
    @RequestMapping("updateOne.do")
    public String updateOne(User user){
        //修改完成跳转页面
        System.out.println("修改");
        service.updateOne(user);
        return "redirect:userList.do";
    }

    //处理文件导入
    @RequestMapping("importUsers.do")
    public String importUsers(HttpServletRequest request) throws FileUploadException, IOException {
        //三步获取文件
        //获取工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //获取文件
        ServletFileUpload upload =new ServletFileUpload(factory);
        //每个文件类型为FileItem 获取到的是集合
        List<FileItem> fis = upload.parseRequest(request);//获取文件集合

        //我们传过来的只有一个input 可以直接取
        FileItem fi =  fis.get(0);//如果上传了多个可以用方法判断 isFormField()
        InputStream is = fi.getInputStream();//从文件中获取文件读取流

        //开始读取内容
        List<User> users =new ArrayList<>();//准备装载excel文件中的内容
        Workbook book = WorkbookFactory.create(is);//jvm版excel文件 相当于一个work文档对象
        Sheet sheet = book.getSheetAt(0);//文档里面的sheet表 导入的文件只有一个sheet表 获取第一个sheet表
        for (int i = 1; i <=sheet.getLastRowNum() ; i++) {//获取表格最后一行的数字
                //第一行是表头不需要 循环每个单元
            Row row = sheet.getRow(i);//获取每行
            Cell c1 = row.getCell(0);//获取五个字段的数据
            Cell c2 = row.getCell(1);
            Cell c3 = row.getCell(2);
            Cell c4 = row.getCell(3);
            Cell c5 = row.getCell(4);
            String uname =c1.getStringCellValue();//获取每个字段里面的值
            String upass = (int)c2.getNumericCellValue() + "";//表格里面密码的数据是数字float
            String truename =c3.getStringCellValue();
            int age = (int)c4.getNumericCellValue();
            String sex =c5.getStringCellValue();
            //组成对象
            User user = new User(null,uname,upass,truename,sex,age,null,null);
            users.add(user);
            is.close();
        }
        service.importUsers(users);
        return "redirect:userList.do";
    }
   //模板下载
    @RequestMapping("uploadTem.do")
    public void uploadTem(HttpServletResponse response) throws IOException {
       //获取文件路径
        //String path = Thread.currentThread().getContextClassLoader().getResource("files/users.xlsx").getPath();
        //获取文件加流
        //InputStream is = new FileInputStream(path);
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("files/users.xlsx");
        //获取输出流
        OutputStream out = response.getOutputStream();
        response.setHeader("content-disposition","attachment;filename=users.xlsx");
        byte[] b = new byte[1024];
        while(true) {
            int n = is.read(b);
            if(n==-1){
                is.close();
                return;
            }
            out.write(b);
            out.flush();
            if(is!=null){
                is.close();
            }

        }


    }

    //批量导出
    @RequestMapping("export.do")
    public void exports(HttpServletResponse response) throws IOException {
        //将数据库所有内容读取出来
        Map<String, Object> map = new HashMap<>();
        map.put("page",1);
        map.put("row",Integer.MAX_VALUE);
        List<User> users = (List<User>) service.findUserByPageAndFilter(map).getList();//可以直接去服务层调用
        //把所有数据写入到表格里面 创建一个虚拟表格对象
//        * poi提供了2个版本workbook对象
//        HSSFWorkbook  xls  后缀文件
//        XSSFWorkbook  xlsx 后缀文件
        Workbook book = new XSSFWorkbook();//创建文档
        Sheet sheet = book.createSheet();//创建表格
        //创建行头和里面的内容
        {
            Row row = sheet.createRow(0);//0索引表示第一行
            Cell c1 = row.createCell(0);//表示第一行的第1个单元格
            Cell c2 = row.createCell(1);
            Cell c3 = row.createCell(2);
            Cell c4 = row.createCell(3);
            c1.setCellValue("用户编号");//给每个格子设置内容
            c2.setCellValue("用户名称");
            c3.setCellValue("真实姓名");
            c4.setCellValue("用户年龄");
        }
        //接下来循环创建每个格子的内容
        int i = 1;//设置变量从索引为1开始
        for (User user : users) {
            Row row = sheet.createRow(i++);//0索引表示第一行
            Cell c1 = row.createCell(0);//表示第一行的第1个单元格
            Cell c2 = row.createCell(1);
            Cell c3 = row.createCell(2);
            Cell c4 = row.createCell(3);
            c1.setCellValue(user.getUno());//给每个格子设置内容
            c2.setCellValue(user.getUname());
            c3.setCellValue(user.getTruename());
            c4.setCellValue(user.getAge());

        }
        //表格赋值完成 发送出去 获取一个流和一个本低存储路径
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            File file = new File(path,"users.xlsx");
            OutputStream out = new FileOutputStream(file);
            book.write(out);//把虚拟表格内的内容输出到文件里面
            //表格内容填充完毕 发送到浏览器
            //文件下载 把当前工程下写好的文件发送到浏览器
            //先读取
            InputStream fis = new FileInputStream(file);
            //输出到浏览器用相应就行
            OutputStream sos = response.getOutputStream();
            byte[]b = new byte[1024];
            response.setHeader("content-disposition","attachment;filename=users.xlsx");
            while(true){
                int n = fis.read(b);
                if(n==-1){
                    break;
                }
                sos.write(b);
                sos.flush();
            }
            fis.close();
            out.close();
    }

    //注销
    @RequestMapping("logout.do")
    public String logout(HttpServletRequest request){
        //清除session
        HttpSession session = request.getSession();
        //session失效 并跳转页面
        session.invalidate();
        return "redirect:index.jsp";//没有传参数一般都是重定向
    }

    //修改密码
    @RequestMapping("setPassword.do")
    @ResponseBody
    public String setPass(@RequestParam("opass") String opass,@RequestParam("npass") String npass,@RequestParam("upass")String upass,HttpServletRequest request){
        //先判断旧密码输入是否正确
        User user = (User)request.getSession().getAttribute("loginUser");
        String pass = user.getUpass();
        if(pass.equals(opass)==false){
            return "原密码输入不正确";
        }
        if(upass.equals(npass)==false){
            return "两次输入的密码有误";
        }
        user.setUpass(upass);
        service.updatePwd(user);
        return "修改密码成功";
    }

}
