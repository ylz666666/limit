package com.dao;

import com.domain.Role;
import com.domain.User;

import java.util.List;
import java.util.Map;

public interface RoleDao {
    //查询过滤后的总条数
    public long total(Map<String,Object> param) ;

    //过滤用户信息
    public List<Role> findRoleByPageAndFilter(Map<String,Object> param) ;

    //新增用户
    void saveUser(Role role);

    //删除单个用户
    void del(Integer uno);
    //删除多个用户
//    void dels(String unos);//服务层处理循环多次

    //查询单个
    User selectOne(Integer uno);

    //修改单个数据
    void updateOne(Role role);

//    //添加多个 直接在服务层处理 循环多次调用添加一个的方法
//    void importUsers(List<User> users);

    //修改密码
    void updatePwd(Role role);
}
