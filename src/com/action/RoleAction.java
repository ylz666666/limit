package com.action;

import com.domain.PageInfo;
import com.domain.Role;
import com.service.RoleService;
import com.service.UserService;
import com.service.impl.RoleServiceImpl;
import com.service.impl.UserServiceImpl;
import mymvc.ModelAndView;
import mymvc.RequestMapping;
import mymvc.RequestParam;
import mymvc.ResponseBody;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleAction {
    private RoleService service = RoleServiceImpl.getService() ;
    //页面加载完成 ajax请求动态数据
    @RequestMapping("roleList.do")
    public PageInfo roleList(@RequestParam("rno") Integer rno, @RequestParam("rname") String rname , @RequestParam("rdes")  String rdes , @RequestParam("page") Integer page , @RequestParam("row") Integer row){
        //查询
        System.out.println("rdes:"+rdes);
        Map<String,Object> map = new HashMap<>();
        map.put("rno",rno);
        map.put("rname",rname);
        map.put("rdes",rdes);
        map.put("page",page);
        map.put("row",row);
        PageInfo pageInfo = service.findRoleByPageAndFilter(map);
        System.out.println("我是pageInfo中的users:"+pageInfo.getList());
        System.out.println("我是pageInfo中的max:"+pageInfo.getMaxPage());
        return pageInfo;
    }

    //查询未分配的角色
    @RequestMapping("findUnlink.do")
    public List<Role> findUnlink(@RequestParam("uno") Integer uno){
        return service.findUnlink(uno);
    }
    //查询yi分配的角色
    @RequestMapping("findlink.do")
    public List<Role> findlink(@RequestParam("uno") Integer uno){
        return service.findlink(uno);
    }

    //修改数据
    @RequestMapping("setRole.do")
    @ResponseBody
    public String setRole(@RequestParam("uno") Integer uno,@RequestParam("rnos") String rnos){
        service.setRole(uno,rnos);
        return "保存成功";
    }
}
