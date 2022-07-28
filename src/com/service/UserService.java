package com.service;

import com.domain.Fn;
import com.domain.PageInfo;
import com.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //验证登录
    public User checkLogin(String uname, String upass);

    //过滤
    public PageInfo findUserByPageAndFilter(Map<String, Object> param);

    //新增
    void saveUser(User user);


    //删除
    void del(Integer uno);

    //删除多个
    void dels(String unos);

    //查询单个
    User selectOne(Integer uno);

    //修改单个数据s
    void updateOne(User user);

    //导入多条
    void importUsers(List<User> user);

    //修改密码
    void updatePwd(User user);

    public List<Fn> getMemu(Integer uno);

    List<Fn> getButton(Integer uno);

    public List<Fn> getAll(Integer uno);

}