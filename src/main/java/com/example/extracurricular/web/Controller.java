package com.example.extracurricular.web;

import com.example.extracurricular.web.command.Command;
import com.example.extracurricular.web.command.CommandContainer;
import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public final class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(Controller.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Command command = CommandContainer.getCommand(req.getServletPath().substring(1));
		log.info(command + " resolved from path: " + req.getServletPath() + ", method: " + req.getMethod());
		command.execute(req, resp);
		log.info(command + " executed");
	}
}
