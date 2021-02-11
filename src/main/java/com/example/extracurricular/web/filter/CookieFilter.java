package com.example.extracurricular.web.filter;

import com.example.extracurricular.util.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public final class CookieFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] cookies = request.getCookies();
        Cookie lang = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lang")) {
                    lang = cookie;
                }
            }
        }
        if (lang == null) {
            lang = new Cookie("lang", "en");
            lang.setMaxAge(Constants.COOKIE_LIFETIME);
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.addCookie(lang);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
