/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@WebFilter(urlPatterns = "*.te")
public class TeacherLoginServlet implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        Object student = req.getSession().getAttribute("teacher");
        if (student == null) {
            System.out.println("教师未登陆");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            System.out.println("放行教师");
        }
    }

    @Override
    public void destroy() {

    }
}
