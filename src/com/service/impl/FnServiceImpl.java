package com.service.impl;

import com.dao.FnDao;
import com.domain.Fn;
import com.service.FnService;
import jdbc.JdbcFront;
import java.util.ArrayList;
import java.util.List;

public class FnServiceImpl implements FnService {
    private FnServiceImpl(){}
    private static volatile FnServiceImpl service = new FnServiceImpl() ;
    public static FnServiceImpl getService(){
        return service ;
    }
    private FnDao dao = new JdbcFront().createDaoImpl(FnDao.class);

    @Override
    public List<Fn> findAll() {
        return findNew(dao.findAll(),-1);
    }
     public void addOne(Fn fn){
        dao.addOne(fn);
    }

    @Override
    public void fnDelete(Integer fno) {
        dao.fnDelete(fno);
    }

    @Override
    public void fnUpdate(Fn fn) {
        dao.fnUpdate(fn);
    }

    //设计一个方法 把得到的所有数据组装成一个新的集合
    private List<Fn> findNew(List<Fn> fns,int pno){//递归方法
        List<Fn> newFns = new ArrayList();
        for (Fn newFn : fns) {
            if(newFn.getPno()==pno){//从根菜单开始
                if(newFn.getFlag()==2){//是一个按钮 直接添加进数组
                    newFns.add(newFn);
                }else{
                    //把集合交给顶级菜单 里面装着它的子集
                    List<Fn> children = findNew(fns, newFn.getFno());//递归
                    newFn.setChildren(children);//把数组存入对象内
                    newFns.add(newFn);
                }
            }

        }

        return newFns;
    }
}
