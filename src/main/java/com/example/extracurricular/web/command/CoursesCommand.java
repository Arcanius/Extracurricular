package com.example.extracurricular.web.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.extracurricular.db.dao.CourseDao;
import com.example.extracurricular.db.dao.CourseDaoImpl;
import com.example.extracurricular.db.model.Course;
import com.example.extracurricular.db.model.User;

public final class CoursesCommand extends Command {
	private static final Logger log = Logger.getLogger(CoursesCommand.class);
	
	private final CourseDao courseDao = CourseDaoImpl.getInstance();
	
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setAttribute("courses", courseDao.getAll());
			req.getRequestDispatcher("jsp/courses.jsp").forward(req, resp);
		} catch (SQLException e) {
			log.error(e.getMessage());
			req.setAttribute("error", "error.db");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (((User) req.getSession().getAttribute("user")).getRole().equals(User.Role.STUDENT)) {
			try {
				int id = Integer.parseInt(req.getParameter("id"));
				Course course = courseDao.getById(id);
				if (course != null) {
					courseDao.enrollUser(course, (User) req.getSession().getAttribute("user"));
				}
				resp.sendRedirect(req.getContextPath() + "/courses");
			} catch (NumberFormatException e) {
				resp.sendRedirect(req.getContextPath() + "/courses");
			} catch (SQLException e) {
				log.error(e.getMessage());
				req.setAttribute("error", "error.db");
	            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
			}
		}
	}
}
