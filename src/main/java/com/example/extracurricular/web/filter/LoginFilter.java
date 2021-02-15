package com.example.extracurricular.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.extracurricular.db.model.User;

import java.io.IOException;

@WebFilter({"/courses/*", "/profile/*", "/users/*"})
public final class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getSession().getAttribute("user") == null) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect(request.getContextPath() + "/login");
        } else if (((User) request.getSession().getAttribute("user")).isBanned()) {
        	HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
