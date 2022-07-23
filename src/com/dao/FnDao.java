package com.dao;

import com.domain.Fn;
import jdbc.annotations.Delete;
import jdbc.annotations.Insert;
import jdbc.annotations.Select;
import jdbc.annotations.Update;

import java.util.List;
import java.util.Map;


public interface FnDao {//查询简单直接用Mybatis注解
     @Select("select * from t_fn where del=1")
     List<Fn> findAll();
     @Insert("insert into t_fn values(null,#{fname},#{fhref},#{flag},#{ftarget},#{pno},1,now(),#{yl1},#{yl2})")
     void addOne(Fn fn);
     @Update("update t_fn set del=2 where fno=#{fno}")
     void fnDelete(Integer fno);
     @Update("update t_fn set fname=#{fname},fhref=#{fhref},flag=#{flag},ftarget=#{ftarget},createtime=now() where fno=#{fno}")
     void fnUpdate(Fn fn);

     @Delete("delete from t_role_fn where rno=#{rno}")
     void delFn(Integer rno);
     @Insert("insert into t_role_fn values(#{rno},#{fno})")
     void addFn(Map map);
     @Select("select fno from t_role_fn where rno=#{rno}")
      List<Integer> getFnos(Integer rno);
      @Select("select * from t_fn where flag=1 and fno in (select fno from t_role_fn where rno in (select rno from t_user_role where uno=#{uno}))")
      List<Fn> getMemu(Integer uno);


      List<Fn> getButton(Integer fno);
}
