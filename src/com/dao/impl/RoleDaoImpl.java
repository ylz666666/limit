package com.dao.impl;
import com.dao.RoleDao;
import com.domain.Role;
import com.domain.User;
import jdbc.JdbcFront;

import java.util.List;
import java.util.Map;

public class RoleDaoImpl implements RoleDao {
    private RoleDaoImpl(){}
    private static RoleDaoImpl dao ;
    public static RoleDaoImpl getDao(){
        if(dao == null){
            synchronized ("dmc") {
                if(dao == null)
                    dao = new RoleDaoImpl();
            }
        }
        return dao ;
    }
    //----------------------------------------

    @Override
    public long total(Map<String, Object> param) {
        Integer rno =(Integer)param.get("rno");
        String rname =(String) param.get("rname");
        String rdes =(String) param.get("rdes");
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from t_role where del = 1 ");
        if(rno!=null){
            sb.append(" and rno=#{rno}");
        }
        if(rname!=null&&!"".equals(rname)){
            sb.append(" and rname like concat(#{rname},'%')");
        }
        if(rdes!=null && "".equals(rdes)){
            sb.append(" and description like concat(#{rdes},'%')");
        }
        JdbcFront jf = new JdbcFront();
        Long aLong = jf.selectOne(sb.toString(), Long.class, param);
        return aLong;
    }

    @Override
    public List<Role> findRoleByPageAndFilter(Map<String, Object> param) {
        Integer rno =(Integer) param.get("rno");
        String rname = (String) param.get("rname");
        String rdes = (String) param.get("rdes");
        Integer page = (Integer) param.get("page");
        Integer row = (Integer) param.get("row");
        Integer start = (Integer)param.get("start");
        StringBuilder sb = new StringBuilder();
        sb.append("select * from t_role where del=1 ");
        if(rno!=null){
            sb.append(" and rno=#{rno}");
        }
        if(rname!=null&&!"".equals(rname)){
            sb.append(" and  rname like concat(#{rname},'%')");
        }
        if(rdes!=null&&!"".equals(rdes)){
            sb.append(" and description like concat(#{rdes},'%')");
        }
        sb.append(" order by createtime,rno limit #{start},#{row}");
        JdbcFront jf = new JdbcFront();
        List<Role> roles = jf.selectList(sb.toString(), Role.class, param);
        return roles;
    }

    //查找未分配的角色
    public List<Role> findUnlink(Integer uno){
        String sql = "select * from t_role where rno not in (select rno from t_user_role where uno=#{uno})";
        JdbcFront jf = new JdbcFront();
        List<Role> roles = jf.selectList(sql, Role.class, uno);
        return roles;
    };
    //查找已分配的角色
    public List<Role> findlink(Integer uno){
        String sql = "select * from t_role where rno in (select rno from t_user_role where uno=#{uno})";
        JdbcFront jf = new JdbcFront();
        List<Role> roles = jf.selectList(sql, Role.class, uno);
        return roles;
    };
    //删除
    public void delRole(Integer uno){
        String sql = "delete from t_user_role where uno=#{uno}";
        JdbcFront jf = new JdbcFront();
        jf.delete(sql,uno);
    }
    //添加
    public void addRole(Map<String,Object> map){
        String sql = "insert into t_user_role values(#{uno},#{rno})";
        JdbcFront jf = new JdbcFront();
        jf.insert(sql,map);
    }
//    //修改
//    public void setRole(Integer uno,String rnos){
//
//    }














    @Override
    public void saveUser(Role role) {

    }

    @Override
    public void del(Integer uno) {

    }

    @Override
    public User selectOne(Integer uno) {
        return null;
    }

    @Override
    public void updateOne(Role role) {

    }

    @Override
    public void updatePwd(Role role) {

    }
}
