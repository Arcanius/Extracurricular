package com.example.extracurricular.web.command;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.extracurricular.db.dao.CourseDao;
import com.example.extracurricular.db.dao.CourseDaoImpl;
import com.example.extracurricular.db.dao.UserDao;
import com.example.extracurricular.db.dao.UserDaoImpl;
import com.example.extracurricular.db.model.Course;
import com.example.extracurricular.db.model.User;

public final class EditCourseCommand extends Command {
	private static final Logger log = Logger.getLogger(EditCourseCommand.class);
	
	private final CourseDao courseDao = CourseDaoImpl.getInstance();
	private final UserDao userDao = UserDaoImpl.getInstance();
	
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			if (req.getParameter("id") == null) {
				resp.sendRedirect(req.getContextPath() + "/courses");
			} else {
				try {
					int id = Integer.parseInt(req.getParameter("id"));
					Course course = courseDao.getById(id);
					if (course == null) {
						resp.sendRedirect(req.getContextPath() + "/courses");
					} else {
						req.setAttribute("course", course);
						req.setAttribute("teachers", userDao.getAllByRole(User.Role.TEACHER));
						req.getRequestDispatcher("jsp/editcourse.jsp").forward(req, resp);
					}
				} catch (NumberFormatException e) {
					log.error(e.getMessage());
					resp.sendRedirect(req.getContextPath() + "/courses");
				}
			}
		} catch (SQLException e) {
            log.error(e.getMessage());
            req.setAttribute("error", "error.db");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
        }
	}
	
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Course course = new Course();
		try {
			course.setId(Integer.parseInt(req.getParameter("id")));
		} catch (NumberFormatException e) {
			course.setId(-1);
		}
		course.setNameEn(req.getParameter("title_en"));
		course.setNameUk(req.getParameter("title_uk"));
		course.setTopicEn(req.getParameter("topic_en"));
		course.setTopicUk(req.getParameter("topic_uk"));
		try {
			course.setStartDate(LocalDate.parse(req.getParameter("start_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		} catch (DateTimeParseException e) {
			course.setStartDate(LocalDate.MIN);
		}
		try {
			course.setDurationInDays(Integer.parseInt(req.getParameter("duration")));
		} catch (NumberFormatException e) {
			course.setDurationInDays(-1);
		}
		try {
			course.setTeacher(userDao.getByLogin(req.getParameter("teacher")));
			Map<String, String> errors = courseDao.validate(course, true);
			if (errors.isEmpty()) {
				courseDao.update(course);
				resp.sendRedirect(req.getContextPath() + "/courses");
			} else {
				errors.forEach(req::setAttribute);
                get(req, resp);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			req.setAttribute("error", "error.db");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}
