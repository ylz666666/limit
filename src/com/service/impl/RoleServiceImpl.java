package com.service.impl;

import com.dao.RoleDao;
import com.dao.UserDao;
import com.dao.impl.RoleDaoImpl;
import com.dao.impl.UserDaoImpl;
import com.domain.PageInfo;
import com.domain.Role;
import com.domain.User;
import com.service.RoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleServiceImpl implements RoleService {
    private RoleServiceImpl(){}
    private static volatile RoleServiceImpl service = new RoleServiceImpl() ;
    public static RoleServiceImpl getService(){
        return service ;
    }
    private RoleDao dao = RoleDaoImpl.getDao();

    @Override
    public PageInfo findRoleByPageAndFilter(Map<String, Object> param) {
        long total = dao.total(param);
        int row =(int) param.get("row");
        int maxPage = (int)Math.ceil((1.0*total)/row) ;
        int page = (int)param.get("page");
        maxPage = Math.max(page,maxPage);
        int start = (page-1)*row;
        param.put("start",start);
        System.out.println("service:"+total+"row:"+row+"max"+maxPage);
        //在这里处理页码的问题然后传给方法
        List<Role> roles = dao.findRoleByPageAndFilter(param);
        return new PageInfo(maxPage,roles);
    }

    //查找未分配的角色
    public List<Role> findUnlink(Integer uno){
        return dao.findUnlink(uno);
    }
    public List<Role> findlink(Integer uno){
        return dao.findlink(uno);
    }

    public void setRole(Integer uno,String rnos){
        //处理逻辑 1.首先把搜索到的全部删除 2.再重新添加
        dao.delRole(uno);//删除所有
        String[] split = rnos.split(",");
        for (int i = 0; i <split.length ; i++) {
            Map<String,Object> map = new HashMap();
            map.put("uno",uno);
            map.put("rno",split[i]);
            dao.addRole(map);
        }

    }









    @Override
    public void saveUser(Role role) {

    }

    @Override
    public void del(Integer rno) {

    }

    @Override
    public void dels(String rno) {

    }

    @Override
    public User selectOne(Integer uno) {
        return null;
    }

    @Override
    public void updateOne(Role role) {

    }

    @Override
    public void importUsers(List<Role> role) {

    }

    @Override
    public void updatePwd(Role role) {

    }
}
