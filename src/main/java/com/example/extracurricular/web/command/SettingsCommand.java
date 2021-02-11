package com.example.extracurricular.web.command;

import com.example.extracurricular.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class SettingsCommand extends Command {
	@Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/settings.jsp").forward(req, resp);
    }

	@Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie lang = new Cookie("lang", req.getParameter("lang"));
        lang.setMaxAge(Constants.COOKIE_LIFETIME);
        resp.addCookie(lang);
        resp.sendRedirect("/");
    }
}
