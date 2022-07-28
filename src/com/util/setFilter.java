package com.util;


import com.domain.Fn;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

//过滤器 组织网页跳转
@WebFilter("/*")//处理所有请求
public class setFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String reqStr = request.getServletPath();//获取请求路径

        //判断访问的路径是否在所有路径里面 不在放行
        //如果在的话 判断是否在用户拥有的路径里面 在的话放行 不在的话响应权限不够
        //获取所有请求的功能
        List<Fn> alls = (List<Fn>)request.getSession().getAttribute("getFnAlls");
        //获取自己的
        List<Fn> owns = (List<Fn>)request.getSession().getAttribute("getAlls");
        System.out.println("alls"+alls);
        if(alls==null){//一开始session里面没有内容 直接放行
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        for (Fn fn : alls) {
            if(fn.getFhref()!=null&&!"".equals(fn.getFhref())&& reqStr.indexOf(fn.getFhref())!=-1){
                for (Fn own : owns) {
                    //是否是自己拥有的权限
                    if(own.getFhref()!=null&&!"".equals(own.getFhref())&&reqStr.indexOf(own.getFhref())!=-1){
                        filterChain.doFilter(servletRequest,servletResponse);
                        return;
                    }
                }
                //没有权限
                //servletResponse.setCharacterEncoding("UTF-8");
                servletResponse.setContentType("text/html;charset=utf-8");
                servletResponse.getWriter().write("没有权限");
                return;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
