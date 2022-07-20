package com.service.impl;

import com.dao.RoleDao;
import com.dao.UserDao;
import com.dao.impl.RoleDaoImpl;
import com.dao.impl.UserDaoImpl;
import com.domain.PageInfo;
import com.domain.Role;
import com.domain.User;
import com.service.RoleService;

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
