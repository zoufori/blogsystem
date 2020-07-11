package com.jimmy.blogsystem.web.interceptor;

import com.jimmy.blogsystem.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义的Interceptor拦截类，用于封装请求后的数据类到request域中，供html页面使用
 * 自定义MVC的Interceptor拦截类有一下两点注意
 * 1、使用@Configuration注解声明
 * 2、自定义注册类将自定义的Interceptor拦截器类进行注册使用
 */
@Configuration
public class BaseInterceptor implements HandlerInterceptor {

    @Autowired
    private Commons commons;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("commons", commons);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
