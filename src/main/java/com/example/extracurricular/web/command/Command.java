package com.example.extracurricular.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Command {
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		switch (req.getMethod()) {
			case "GET" -> get(req, resp);
			case "POST" -> post(req, resp);
		}
	}
	
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getServletPath());
	}
	
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getServletPath());
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
