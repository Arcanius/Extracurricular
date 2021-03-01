package com.example.extracurricular.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class FilterCommand extends Command {
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.setAttribute("orderBy", req.getParameter("orderBy"));
		session.setAttribute("order", req.getParameter("order"));
		session.setAttribute("topic", req.getParameter("topic"));
		session.setAttribute("teacher", req.getParameter("teacher"));
		resp.sendRedirect(req.getContextPath() + "/courses");
	}
}
