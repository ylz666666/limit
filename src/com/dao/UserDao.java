package com.dao;

import com.domain.Fn;
import com.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    //验证用户登录
    public User findUserByNameAndPass(String uname , String upass) ;
    //查询总条数
    public long total(Map<String,Object> param) ;
    //过滤用户信息
    public List<User> findUserByPageAndFilter(Map<String,Object> param) ;

    //新增用户
     void saveUser(User user);

     //删除单个用户
    void del(Integer uno);
    //删除多个用户
//    void dels(String unos);//服务层处理循环多次

    //查询单个
    User selectOne(Integer uno);

    //修改单个数据
    void updateOne(User user);

//    //添加多个 直接在服务层处理 循环多次调用添加一个的方法
//    void importUsers(List<User> users);

    //修改密码
    void updatePwd(User user);


    public List<Fn> getMemu(Integer uno);

    public List<Fn> getButton(Integer uno);

}
