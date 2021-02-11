package com.example.extracurricular.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.example.extracurricular.db.model.Course;

public interface CourseDao {
	void save(Course course) throws SQLException;
	
	List<Course> getAll() throws SQLException;
}
