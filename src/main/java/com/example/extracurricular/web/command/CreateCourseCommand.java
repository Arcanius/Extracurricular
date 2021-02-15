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
import com.example.extracurricular.db.model.Course;

public final class CreateCourseCommand extends Command {
	private static final Logger log = Logger.getLogger(CreateCourseCommand.class);
	
	private final CourseDao courseDao = CourseDaoImpl.getInstance();
	
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("jsp/createcourse.jsp").forward(req, resp);
	}
	
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Course course = new Course();
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
			Map<String, String> errors = courseDao.validate(course);
			if (errors.isEmpty()) {
				courseDao.save(course);
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
