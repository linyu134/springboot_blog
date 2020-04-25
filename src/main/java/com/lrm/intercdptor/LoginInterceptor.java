package com.lrm.intercdptor;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //用户没有登录进不去admin下的界面
        if(request.getSession().getAttribute("user") == null){
            //重定向到admin
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }

}
