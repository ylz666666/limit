package com.action;

import com.domain.Fn;
import com.service.FnService;
import com.service.RoleService;
import com.service.impl.FnServiceImpl;
import com.service.impl.RoleServiceImpl;
import mymvc.RequestMapping;
import mymvc.RequestParam;
import mymvc.ResponseBody;

import java.util.List;


public class FnAction {
    private FnService service = FnServiceImpl.getService() ;
    @RequestMapping("fnList.do")
    @ResponseBody
    public List<Fn> findAll(){
        return service.findAll();
    }

    @RequestMapping("addOne.do")
    @ResponseBody
    public void addOne(Fn fn){
        service.addOne(fn);
    }
    @RequestMapping("fnDelete.do")
    @ResponseBody
    public void fnDelete(@RequestParam("fno")Integer fno){
        System.out.println("控制台"+fno);
        service.fnDelete(fno);
    }
    @RequestMapping("fnUpdate.do")
    @ResponseBody
    public void fnUpdate(Fn fn){
        service.fnUpdate(fn);
    }

    @RequestMapping("setFn.do")
    @ResponseBody
    public String setFn(@RequestParam("rno") Integer rno,@RequestParam("fnos") String fnos){
        service.setFn(rno,fnos);
        return "保存成功";
    }

    //获取对应功能
    @RequestMapping("getFnos.do")
    public List<Integer> getFnos(@RequestParam("rno") Integer rno){
        return service.getFnos(rno);
    }

}
