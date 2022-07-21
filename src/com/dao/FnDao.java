package com.dao;

import com.domain.Fn;
import jdbc.annotations.Insert;
import jdbc.annotations.Select;
import jdbc.annotations.Update;

import java.util.List;


public interface FnDao {//查询简单直接用Mybatis注解
     @Select("select * from t_fn where del=1")
     List<Fn> findAll();
     @Insert("insert into t_fn values(null,#{fname},#{fhref},#{flag},#{ftarget},#{pno},1,now(),#{yl1},#{yl2})")
     void addOne(Fn fn);
     @Update("update t_fn set del=2 where fno=#{fno}")
     void fnDelete(Integer fno);
     @Update("update t_fn set fname=#{fname},fhref=#{fhref},flag=#{flag},ftarget=#{ftarget},createtime=now() where fno=#{fno}")
     void fnUpdate(Fn fn);
}
