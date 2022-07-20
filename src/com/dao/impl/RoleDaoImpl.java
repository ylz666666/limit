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
        System.out.println("老规矩"+sb+"编号"+rno+"开始:"+start+"row"+row+"page:"+page);
        return roles;
    }

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
