package com.example.extracurricular.web.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.extracurricular.db.dao.CourseDao;
import com.example.extracurricular.db.dao.CourseDaoImpl;
import com.example.extracurricular.db.model.User;

public final class ProfileCommand extends Command {
	private static final Logger log = Logger.getLogger(ProfileCommand.class);
	
	private final CourseDao courseDao = CourseDaoImpl.getInstance();
	
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setAttribute("courses", courseDao.getStudentCourses(((User) req.getSession().getAttribute("user")).getId()));
			req.getRequestDispatcher("jsp/profile.jsp").forward(req, resp);
		} catch (SQLException e) {
			log.error(e.getMessage());
			req.setAttribute("error", "error.db");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}
