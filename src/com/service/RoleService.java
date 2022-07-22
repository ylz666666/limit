package com.service;

import com.domain.PageInfo;
import com.domain.Role;
import com.domain.User;

import java.util.List;
import java.util.Map;

public interface RoleService {
    //过滤
    public PageInfo findRoleByPageAndFilter(Map<String, Object> param);

    //新增
    void saveUser(Role role);

    //查找未分配的角色
     List<Role> findUnlink(Integer uno);

     List<Role> findlink(Integer uno);

    void setRole(Integer uno,String rnos);












    //删除
    void del(Integer rno);

    //删除多个
    void dels(String rnos);

    //查询单个
    User selectOne(Integer rno);

    //修改单个数据s
    void updateOne(Role role);

    //导入多条
    void importUsers(List<Role> role);

    //修改密码
    void updatePwd(Role role);
}
