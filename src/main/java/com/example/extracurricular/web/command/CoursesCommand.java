package com.example.extracurricular.web.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.example.extracurricular.db.dao.CourseDao;
import com.example.extracurricular.db.dao.CourseDaoImpl;
import com.example.extracurricular.db.dao.UserDao;
import com.example.extracurricular.db.dao.UserDaoImpl;
import com.example.extracurricular.db.model.Course;
import com.example.extracurricular.db.model.User;
import com.example.extracurricular.db.model.User.Role;
import com.example.extracurricular.util.Constants;

public final class CoursesCommand extends Command {
	private static final Logger log = Logger.getLogger(CoursesCommand.class);
	
	private final CourseDao courseDao = CourseDaoImpl.getInstance();
	private final UserDao userDao = UserDaoImpl.getInstance();
	
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int page = 1;
		try {
			page = Integer.parseInt(req.getParameter("page"));
			if (page < 1) {
				page = 1;
			}
		} catch (NumberFormatException e) {
			page = 1;
		}
		String lang = "";
		for (Cookie cookie : req.getCookies()) {
			if ("lang".equals(cookie.getName())) {
				lang = cookie.getValue();
				break;
			}
		}
		try {
			HttpSession session = req.getSession();
			User user = (User) session.getAttribute("user");
			req.setAttribute("courses", courseDao.getForPage(user, page, (String) session.getAttribute("orderBy"), (String) session.getAttribute("order"), (String) session.getAttribute("topic"), (String) session.getAttribute("teacher"), lang));
			int count = courseDao.countCourses(user);
			req.setAttribute("count", count);
			req.setAttribute("pages", count % Constants.RECORDS_PER_PAGE == 0 ? count / Constants.RECORDS_PER_PAGE : count / Constants.RECORDS_PER_PAGE + 1);
			req.setAttribute("topics", courseDao.getAllTopics(lang));
			req.setAttribute("teachers", userDao.getAllByRole(Role.TEACHER));
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
				if (course != null && !courseDao.isCourseStarted(id) && !courseDao.isStudentEnrolled(((User) req.getSession().getAttribute("user")).getId(), id)) {
					courseDao.enrollUser(course, (User) req.getSession().getAttribute("user"));
					resp.sendRedirect(req.getContextPath() + "/profile");
				} else {
					resp.sendRedirect(req.getContextPath() + "/courses");
				}
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
