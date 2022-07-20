package com.dao.impl;

import com.dao.UserDao;
import com.domain.User;
import jdbc.JdbcFront;
import mymvc.RequestMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private UserDaoImpl(){}
    private static UserDaoImpl dao ;
    public static UserDaoImpl getDao(){
        if(dao == null){
            synchronized ("dmc") {
                if(dao == null)
                    dao = new UserDaoImpl();
            }
        }

        return dao ;
    }
    //----------------------------------------

    @Override
    public User findUserByNameAndPass(String uname, String upass) {
        String sql = "select * from t_user where uname=#{uname} and upass = #{upass} and del=1";
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("uname",uname);
        param.put("upass",upass);
        System.out.println(uname+"---"+upass);
        JdbcFront jdbc = new JdbcFront();
        //使用jdbc框架执行sql语句实现查找操作，将查询结果组成User类型对象，执行sql是需要的参数在param集合中
        return jdbc.selectOne(sql,User.class,param) ;

    }
    //-------------------------查询----------------------------
    @Override
    public long total(Map<String, Object> param) {
        //查询总数
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from t_user where del=1");
        Integer uno = (Integer) param.get("uno");
        if(uno!=null){
                sb.append(" and uno=#{uno} ");
        }
        String uname = (String)param.get("uname");
        if(uname!=null&&!"".equals(uname)){
            sb.append(" and uname like concat(#{uname},'%') ");
        }
        String sex =(String) param.get("sex");
        if(sex!=null&&!"".equals(sex)){
            sb.append(" and sex=#{sex} ");
        }
        JdbcFront jf = new JdbcFront();
        Long aLong = jf.selectOne(sb.toString(), Long.class, param);
        return aLong;
    }

    @Override
    public List<User> findUserByPageAndFilter(Map<String, Object> param) {
        //查询过滤后的数据
        StringBuilder sb = new StringBuilder();
        Integer uno = (Integer) param.get("uno");
        sb.append("select * from t_user where del=1");
        if(uno!=null){
            sb.append(" and uno=#{uno} ");
        }
        String uname = (String)param.get("uname");
        if(uname!=null&&!"".equals(uname)){
            sb.append(" and uname like concat(#{uname},'%') ");
        }
        String sex =(String) param.get("sex");
        if(sex!=null&&!"".equals(sex)){
            sb.append(" and sex=#{sex} ");
        }
        sb.append(" order by createtime desc,uno  limit #{start},#{length}");
        JdbcFront jf = new JdbcFront();
        List<User> users = jf.selectList(sb.toString(), User.class, param);
        return users;
    }

    //新增
    @Override
    public void saveUser(User user) {
        System.out.println("新增");
        StringBuilder sb = new StringBuilder();
        sb.append("insert into t_user values(null,#{uname},#{upass},#{truename},#{sex},#{age},1,now(),#{yl1},#{yl2})");
        JdbcFront jf = new JdbcFront();
        System.out.println(sb.toString());
        jf.insert(sb.toString(), user);

    }

    //删除单个
    @Override
    public void del(Integer uno) {
        String sql = "update t_user set del=0 where uno=#{uno}";
        JdbcFront jf = new JdbcFront();
        jf.update(sql,uno);
    }

//    public void dels(String unos){//删除多个
//        String[] un = unos.split(",");
//        JdbcFront jf = new JdbcFront();
//        for (int i = 0; i <un.length ; i++) {
//            int uno = Integer.parseInt(un[i]);
//            String sql = "update t_user set del=0 where uno in (#{uno})";
//            jf.update(sql,uno);
//        }
//
//   }

    //查询单个
    @Override
    public User selectOne(Integer uno) {
        String sql = "select * from t_user where uno=#{uno}";
        JdbcFront jf = new JdbcFront();
        User user = jf.selectOne(sql,User.class,uno);
        return user;
    }

    @Override
    public void updateOne(User user) {
        String sql = "update t_user set uname=#{uname},truename=#{truename},age=#{age},sex=#{sex} where uno=#{uno}";
        JdbcFront jf = new JdbcFront();
        int update = jf.update(sql, user);

    }

    public void updatePwd(User user){
        String sql = "update t_user set upass=#{upass} where uno=#{uno}";
        JdbcFront jf = new JdbcFront();
        int update = jf.update(sql,user);
    }

}
