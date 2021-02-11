package com.example.extracurricular.db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.example.extracurricular.db.model.Course;
import com.example.extracurricular.util.DBCPDataSource;

public final class CourseDaoImpl implements CourseDao {
	private static CourseDao instance;
	private final DBCPDataSource dataSource;
    private static final Logger log = Logger.getLogger(CourseDaoImpl.class);
    
    private CourseDaoImpl() {
    	dataSource = DBCPDataSource.getInstance();
    }
    
    public static synchronized CourseDao getInstance() {
        if (instance == null) {
            instance = new CourseDaoImpl();
        }
        return instance;
    }
    
    @Override
    public void save(Course course) throws SQLException {
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT * FROM topic WHERE name_en = ? AND name_uk = ?";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, course.getNameEn());
        	statement.setString(2, course.getNameUk());
        	resultSet = statement.executeQuery();
        	statement.close();
        	if (!resultSet.next()) {
        		query = "INSERT INTO topic (name_en, name_uk) VALUES (?, ?)";
        		statement = connection.prepareStatement(query);
        		statement.setString(1, course.getNameEn());
        		statement.setString(2, course.getNameUk());
        		statement.executeUpdate();
        		statement.close();
        	}
        	query = "INSERT INTO course (name_en, name_uk, topic_id, start_date, duration_in_days, price) VALUES (?, ?, (SELECT id FROM topic WHERE name_en = ? AND name_uk = ?), ?, ?, ?)";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, course.getNameEn());
        	statement.setString(2, course.getNameUk());
        	statement.setString(3, course.getTopicEn());
        	statement.setString(4, course.getTopicUk());
        	statement.setObject(5, course.getStartDate());
        	statement.setInt(6, course.getDurationInDays());
        	statement.setInt(7, course.getPrice());
        	statement.executeUpdate();
        	log.info("Course " + course + " saved in database");
        } catch (SQLException e) {
        	log.error(e.getMessage());
            throw e;
        } finally {
			close(connection, statement, resultSet);
		}
    }
    
    @Override
    public List<Course> getAll() throws SQLException {
    	Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Course> courses = new ArrayList<>();
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT course.id, course.name_en, course.name_uk, topic.name_en, topic.name_uk, start_date, duration_in_days, price, teacher_id FROM course INNER JOIN topic ON topic_id = topic.id";
        	statement = connection.createStatement();
        	resultSet = statement.executeQuery(query);
        	while (resultSet.next()) {
        		Course course = new Course();
        		course.setId(resultSet.getInt(1));
        		course.setNameEn(resultSet.getString(2));
        		course.setNameUk(resultSet.getString(3));
        		course.setTopicEn(resultSet.getString(4));
        		course.setTopicUk(resultSet.getString(5));
        		course.setStartDate(((Date) resultSet.getObject(6)).toLocalDate());
        		course.setDurationInDays(resultSet.getInt(7));
        		course.setPrice(resultSet.getInt(8));
        		course.setTeacher(UserDaoImpl.getInstance().getById(resultSet.getInt(9)));
        		courses.add(course);
        	}
        	return courses;
        } catch (SQLException e) {
        	log.error(e.getMessage());
        	throw e;
        } finally {
        	close(connection, statement, resultSet);
        }
    }
    
    private void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
