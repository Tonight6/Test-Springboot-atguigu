package com.springboot.Interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: LoginHandlerInterceptor
 * Package: com.springboot.Interceptor
 *
 * @Description:
 * @Date: 2021/6/1 8:13
 * @author: 浪漫
 */

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        System.out.println("进入拦截器=======");
        if (subject.isAuthenticated() || subject.isRemembered()){
            request.getRequestDispatcher("/success.html").forward(request,response);
            return true;
        }else {
            //未登陆，返回登陆页面
            request.setAttribute("msg","没有权限请先登陆");
            response.sendRedirect("/index.html");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
