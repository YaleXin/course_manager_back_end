/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.filter;



import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@WebFilter(urlPatterns = "/*")
public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("SessionFilter");



        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        System.out.println("before: ");
        System.out.println(request.getHeader("Access-Control-Allow-Origin"));


        HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);
        requestWrapper.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8081");
        requestWrapper.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        requestWrapper.addHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        requestWrapper.addHeader("Access-Control-Allow-Credentials", "true");
        requestWrapper.addHeader("token","aaa");


        filterChain.doFilter(requestWrapper, response);
//        filterChain.doFilter(mutableRequest, servletResponse);
    }


    @Override
    public void destroy() {

    }
}
