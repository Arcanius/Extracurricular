package com.example.extracurricular.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class FilterCommand extends Command {
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("orderBy", req.getParameter("orderBy"));
		resp.sendRedirect(req.getContextPath() + "/courses");
	}
}
