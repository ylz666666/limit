package com.service.impl;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.domain.Fn;
import com.domain.PageInfo;
import com.domain.User;
import com.service.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserServiceImpl(){}
    private static volatile UserServiceImpl service = new UserServiceImpl() ;
    public static UserServiceImpl getService(){
        return service ;
    }


    private UserDao dao = UserDaoImpl.getDao();

    @Override
    public User checkLogin(String uname, String upass) {
        System.out.println(uname+"---"+upass);
        return dao.findUserByNameAndPass(uname,upass);
    }


    //---------------------------查询------------------------
    @Override
    public PageInfo findUserByPageAndFilter(Map<String, Object> param) {
        //1.处理下前端传过来的页数
        Integer page =(Integer) param.get("page");
        Integer row = (Integer) param.get("row");
        //获取最大页数
        Long total = dao.total(param);
        System.out.println("total:"+total);
        int maxPage = total==null?-1:(int)Math.ceil((1.0*total)/row);
        //对最大页数进行筛选 让最大页数最小不为0 为1
        maxPage = Math.max(1,maxPage);
        //判断页数 如果用户输入大的数据超出 页数范围 则取最大页码数
        page = Math.min(page,maxPage);
        //获取开始的地方
        int start = (page-1) * row;
        //获取条数
        int length = row;
        System.out.println("page:"+start+"row:"+length);
        param.put("start",start);
        param.put("length",length);
        List<User> users = dao.findUserByPageAndFilter(param);

        return new PageInfo(maxPage,users);
    }

    @Override
    public void saveUser(User user) {
         dao.saveUser(user);
    }

    @Override
    public void del(Integer uno) {
        dao.del(uno);
    }
    public void dels(String unos){
        String[] un = unos.split(",");
        for (int i = 0; i <un.length ; i++) {
            int uno = Integer.parseInt(un[i]);
            this.del(uno);
        }
    }
    @Override
    public User selectOne(Integer uno) {
        return dao.selectOne(uno);
    }

    public void updateOne(User user){
        dao.updateOne(user);
    }

    public void importUsers(List<User> users) {
        for (User user : users) {
            dao.saveUser(user);
        }
    }
    public void updatePwd(User user){
       dao.updatePwd(user);
    }

    public List<Fn> getMemu(Integer uno){

        return dao.getMemu(uno);
    }
    public List<Fn> getButton(Integer uno){
        return dao.getButton(uno);
    }
}
