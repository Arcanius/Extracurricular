package com.example.extracurricular.db.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.example.extracurricular.db.model.Course;
import com.example.extracurricular.db.model.StudentCourse;
import com.example.extracurricular.db.model.User;

public interface CourseDao {
	void save(Course course) throws SQLException;
	
	void update(Course course) throws SQLException;
	
	List<Course> getAll() throws SQLException;
	
	Course getById(int id) throws SQLException;
	
	Map<String, String> validate(Course course, boolean edit) throws SQLException;
	
	void enrollUser(Course course, User user) throws SQLException;
	
	List<StudentCourse> getStudentCourses(int studentId) throws SQLException;
	
	boolean isStudentEnrolled(int studentId, int courseId) throws SQLException;
	
	int countCourses() throws SQLException;
	
	List<Course> getForPage(int page, String orderBy, String lang) throws SQLException;
}
