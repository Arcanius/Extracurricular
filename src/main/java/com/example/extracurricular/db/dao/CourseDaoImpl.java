package com.example.extracurricular.db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.example.extracurricular.db.model.Course;
import com.example.extracurricular.db.model.StudentCourse;
import com.example.extracurricular.db.model.User;
import com.example.extracurricular.util.Constants;
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
        	String query = "SELECT * FROM topic WHERE name_en = ? OR name_uk = ?";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, course.getTopicEn());
        	statement.setString(2, course.getTopicUk());
        	resultSet = statement.executeQuery();
        	if (!resultSet.next()) {
        		statement.close();
        		query = "INSERT INTO topic (name_en, name_uk) VALUES (?, ?)";
        		statement = connection.prepareStatement(query);
        		statement.setString(1, course.getTopicEn());
        		statement.setString(2, course.getTopicUk());
        		statement.executeUpdate();
        	}
        	statement.close();
        	query = "INSERT INTO course (name_en, name_uk, topic_id, start_date, duration_in_days) VALUES (?, ?, (SELECT id FROM topic WHERE name_en = ? OR name_uk = ?), ?, ?)";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, course.getNameEn());
        	statement.setString(2, course.getNameUk());
        	statement.setString(3, course.getTopicEn());
        	statement.setString(4, course.getTopicUk());
        	statement.setObject(5, course.getStartDate());
        	statement.setInt(6, course.getDurationInDays());
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
    public void update(Course course) throws SQLException {
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT * FROM topic WHERE name_en = ? OR name_uk = ?";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, course.getTopicEn());
        	statement.setString(2, course.getTopicUk());
        	resultSet = statement.executeQuery();
        	if (!resultSet.next()) {
        		statement.close();
        		query = "INSERT INTO topic (name_en, name_uk) VALUES (?, ?)";
        		statement = connection.prepareStatement(query);
        		statement.setString(1, course.getTopicEn());
        		statement.setString(2, course.getTopicUk());
        		statement.executeUpdate();
        	}
        	statement.close();
        	query = "UPDATE course SET name_en = ?, name_uk = ?, topic_id = (SELECT id FROM topic WHERE name_en = ? OR name_uk = ?), start_date = ?, duration_in_days = ?, teacher_id = (SELECT user.id FROM user INNER JOIN role ON role_id = role.id WHERE user.id = ? AND role.name = 'TEACHER') WHERE id = ?";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, course.getNameEn());
        	statement.setString(2, course.getNameUk());
        	statement.setString(3, course.getTopicEn());
        	statement.setString(4, course.getTopicUk());
        	statement.setObject(5, course.getStartDate());
        	statement.setInt(6, course.getDurationInDays());
        	if (course.getTeacher() != null) {
        		statement.setInt(7, course.getTeacher().getId());
        	} else {
        		statement.setInt(7, -1);
        	}
        	statement.setInt(8, course.getId());
        	statement.executeUpdate();
        	log.info("Course " + course + " updated in database");
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
        	String query = "SELECT course.id, course.name_en, course.name_uk, topic.name_en, topic.name_uk, start_date, duration_in_days, teacher_id, (SELECT COUNT(course_id) FROM student_course WHERE course.id = course_id) FROM course INNER JOIN topic ON topic_id = topic.id";
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
        		course.setTeacher(UserDaoImpl.getInstance().getById(resultSet.getInt(8)));
        		course.setStudents(resultSet.getInt(9));
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
    
    @Override
    public List<String> getAllTopics(String lang) throws SQLException {
    	Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> topics = new ArrayList<>();
        try {
        	connection = dataSource.getConnection();
        	String query = "";
        	if ("uk".equals(lang)) {
        		query = "SELECT name_uk FROM topic";
        	} else {
        		query = "SELECT name_en FROM topic";
        	}
        	statement = connection.createStatement();
        	resultSet = statement.executeQuery(query);
        	while (resultSet.next()) {
        		topics.add(resultSet.getString(1));
        	}
        	return topics;
        } catch (SQLException e) {
        	log.error(e.getMessage());
        	throw e;
        } finally {
        	close(connection, statement, resultSet);
        }
    }
    
    @Override
    public Course getById(int id) throws SQLException {
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT course.id, course.name_en, course.name_uk, topic.name_en, topic.name_uk, start_date, duration_in_days, teacher_id FROM course INNER JOIN topic ON topic_id = topic.id WHERE course.id = ?";
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, id);
        	resultSet = statement.executeQuery();
        	Course course = null;
        	if (resultSet.next()) {
        		course = new Course();
        		course.setId(resultSet.getInt(1));
        		course.setNameEn(resultSet.getString(2));
        		course.setNameUk(resultSet.getString(3));
        		course.setTopicEn(resultSet.getString(4));
        		course.setTopicUk(resultSet.getString(5));
        		course.setStartDate(((Date) resultSet.getObject(6)).toLocalDate());
        		course.setDurationInDays(resultSet.getInt(7));
        		course.setTeacher(UserDaoImpl.getInstance().getById(resultSet.getInt(8)));
        	}
        	log.info("Course " + course + " provided by database");
        	return course;
        } catch (SQLException e) {
        	log.error(e.getMessage());
        	throw e;
        } finally {
        	close(connection, statement, resultSet);
        }
    }
    
    @Override
    public Map<String, String> validate(Course course, boolean edit) throws SQLException {
    	Map<String, String> errors = new HashMap<>();
        if (!edit) {
        	Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
            	connection = dataSource.getConnection();
            	String query = "SELECT name_en, name_uk FROM course WHERE name_en = ? OR name_uk = ?";
            	statement = connection.prepareStatement(query);
            	statement.setString(1, course.getNameEn());
            	statement.setString(2, course.getNameUk());
            	resultSet = statement.executeQuery();
            	if (resultSet.next()) {
            		if (course.getNameEn().equals(resultSet.getString(1))) {
            			errors.put("titleEn.error", "error.title.busy");
            		}
            		if (course.getNameUk().equals(resultSet.getString(2))) {
            			errors.put("titleUk.error", "error.title.busy");
            		}
            	}
            } catch (SQLException e) {
            	log.error(e.getMessage());
            	throw e;
            } finally {
            	close(connection, statement, resultSet);
            }
        }
        if (!course.getNameEn().matches("[A-Z][A-Za-z ]+[A-Za-z]")) {
        	errors.put("titleEn.error", "error.title.wrong");
        }
        if (!course.getNameUk().matches("[А-ЩЬЮЯҐЄІЇ][А-ЩЬЮЯҐЄІЇа-щьюяґєії' ]+[А-ЩЬЮЯҐЄІЇа-щьюяґєії]")) {
        	errors.put("titleUk.error", "error.title.wrong");
        }
        if (!course.getTopicEn().matches("[A-Z][A-Za-z ]+[A-Za-z]")) {
        	errors.put("topicEn.error", "error.topic.wrong");
        }
        if (!course.getTopicUk().matches("[А-ЩЬЮЯҐЄІЇ][А-ЩЬЮЯҐЄІЇа-щьюяґєії' ]+[А-ЩЬЮЯҐЄІЇа-щьюяґєії]")) {
        	errors.put("topicUk.error", "error.topic.wrong");
        }
        if (course.getStartDate().equals(LocalDate.MIN)) {
        	errors.put("start_date.error", "error.start_date.wrong");
        }
        if (course.getDurationInDays() <= 0) {
        	errors.put("duration.error", "error.duration.wrong");
        }
        return errors;
    }
    
    @Override
    public void enrollUser(Course course, User user) throws SQLException {
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "INSERT INTO student_course (student_id, course_id, progress, mark) VALUES (?, ?, 0, 0)";
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, user.getId());
        	statement.setInt(2, course.getId());
        	statement.executeUpdate();
        	log.info("User " + user + " enrolled in course " + course);
        } catch (SQLException e) {
        	log.error(e.getMessage());
        	throw e;
        } finally {
        	close(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<StudentCourse> getStudentCourses(int studentId) throws SQLException {
    	List<StudentCourse> courses = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT course_id, progress, mark FROM student_course WHERE student_id = ?";
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, studentId);
        	resultSet = statement.executeQuery();
        	while (resultSet.next()) {
        		StudentCourse course = new StudentCourse();
        		course.setCourse(getById(resultSet.getInt(1)));
        		course.setProgress(resultSet.getInt(2));
        		course.setMark(resultSet.getInt(3));
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
    
    @Override
    public boolean isStudentEnrolled(int studentId, int courseId) throws SQLException {
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT * FROM student_course WHERE student_id = ? AND course_id = ?";
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, studentId);
        	statement.setInt(2, courseId);
        	resultSet = statement.executeQuery();
        	if (resultSet.next()) {
        		return true;
        	}
        	return false;
        } catch (SQLException e) {
        	log.error(e.getMessage());
        	throw e;
        } finally {
        	close(connection, statement, resultSet);
        }
    }
    
    @Override
    public int countCourses(User user) throws SQLException {
    	return switch (user.getRole()) {
    		case ADMIN -> countCoursesForAdmin();
    		case STUDENT -> countCoursesForStudent(user.getId());
    		default -> 0;
    	};
    }
    
    private int countCoursesForAdmin() throws SQLException {
    	Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT COUNT(id) FROM course";
        	statement = connection.createStatement();
        	resultSet = statement.executeQuery(query);
        	resultSet.next();
        	return resultSet.getInt(1);
        } catch (SQLException e) {
        	log.error(e.getMessage());
        	throw e;
        } finally {
        	close(connection, statement, resultSet);
        }
    }
    
    private int countCoursesForStudent(int id) throws SQLException {
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT COUNT(id) FROM course WHERE id NOT IN (SELECT course_id FROM student_course WHERE student_id = ?) AND CURRENT_DATE() < start_date";
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, id);
        	resultSet = statement.executeQuery();
        	resultSet.next();
        	return resultSet.getInt(1);
        } catch (SQLException e) {
        	log.error(e.getMessage());
        	throw e;
        } finally {
        	close(connection, statement, resultSet);
        }
    }
    
    @Override
    public boolean isCourseStarted(int id) throws SQLException {
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT id FROM course WHERE id = ? AND start_date < CURRENT_DATE()";
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, id);
        	resultSet = statement.executeQuery();
        	return resultSet.next();
        } catch (SQLException e) {
        	log.error(e.getMessage());
        	throw e;
        } finally {
        	close(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<Course> getForPage(User user, int page, String orderBy, String order, String topic, String teacherLogin, String lang) throws SQLException {
    	return switch (user.getRole()) {
    		case ADMIN -> getForAdmin(page, orderBy, order, topic, teacherLogin, lang);
    		case STUDENT -> getForStudent(user, page, orderBy, order, topic, teacherLogin, lang);
    		default -> new ArrayList<Course>();
    	};
    }
    
    private List<Course> getForAdmin(int page, String orderBy, String order, String topic, String teacherLogin, String lang) throws SQLException {
    	List<Course> courses = new ArrayList<>();
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT course.id, course.name_en, course.name_uk, topic.name_en, topic.name_uk, start_date, duration_in_days, teacher_id, (SELECT COUNT(course_id) FROM student_course WHERE course.id = course_id) FROM course INNER JOIN topic ON topic_id = topic.id";
        	if (!"all".equals(topic) || !"all".equals(teacherLogin)) {
        		query += " WHERE";
        		if (!"all".equals(topic)) {
        			query += " (topic.name_en = ? OR topic.name_uk = ?)";
        			if (!"all".equals(teacherLogin)) {
        				query += " AND";
        			}
        		}
        		if (!"all".equals(teacherLogin)) {
        			query += " teacher_id IN (SELECT id FROM user WHERE login = ?)";
        		}
        	}
        	if ("title".equals(orderBy)) {
        		if ("uk".equals(lang)) {
        			query += " ORDER BY course.name_uk";
        		} else {
        			query += " ORDER BY course.name_en";
        		}
        	}
        	switch (orderBy) {
        		case "duration" -> query += " ORDER BY duration_in_days";
        		case "students" -> query += " ORDER BY (SELECT COUNT(course_id) FROM student_course WHERE course.id = course_id)";
        	}
        	if ("desc".equals(order) && !"none".equals(orderBy)) {
        		query += " DESC";
        	}
        	query += " LIMIT ?, ?";
        	statement = connection.prepareStatement(query);
        	int counter = 1;
        	if (!"all".equals(topic)) {
        		statement.setString(counter, topic);
        		++counter;
        		statement.setString(counter, topic);
        		++counter;
        	}
        	if (!"all".equals(teacherLogin)) {
        		statement.setString(counter, teacherLogin);
        		++counter;
        	}
        	statement.setInt(counter, (page - 1) * Constants.RECORDS_PER_PAGE);
        	++counter;
        	statement.setInt(counter, Constants.RECORDS_PER_PAGE);
        	resultSet = statement.executeQuery();
        	while (resultSet.next()) {
        		Course course = new Course();
        		course.setId(resultSet.getInt(1));
        		course.setNameEn(resultSet.getString(2));
        		course.setNameUk(resultSet.getString(3));
        		course.setTopicEn(resultSet.getString(4));
        		course.setTopicUk(resultSet.getString(5));
        		course.setStartDate(((Date) resultSet.getObject(6)).toLocalDate());
        		course.setDurationInDays(resultSet.getInt(7));
        		course.setTeacher(UserDaoImpl.getInstance().getById(resultSet.getInt(8)));
        		course.setStudents(resultSet.getInt(9));
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
    
    private List<Course> getForStudent(User student, int page, String orderBy, String order, String topic, String teacherLogin, String lang) throws SQLException {
    	List<Course> courses = new ArrayList<>();
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT course.id, course.name_en, course.name_uk, topic.name_en, topic.name_uk, start_date, duration_in_days, teacher_id, (SELECT COUNT(course_id) FROM student_course WHERE course.id = course_id) FROM course INNER JOIN topic ON topic_id = topic.id WHERE course.id NOT IN (SELECT course_id FROM student_course WHERE student_id = ?) AND CURRENT_DATE() < start_date";
        	if (!"all".equals(topic)) {
    			query += " AND (topic.name_en = ? OR topic.name_uk = ?)";
    		}
    		if (!"all".equals(teacherLogin)) {
    			query += " AND teacher_id IN (SELECT id FROM user WHERE login = ?)";
    		}
        	if ("title".equals(orderBy)) {
        		if ("uk".equals(lang)) {
        			query += " ORDER BY course.name_uk";
        		} else {
        			query += " ORDER BY course.name_en";
        		}
        	}
        	switch (orderBy) {
        		case "duration" -> query += " ORDER BY duration_in_days";
        		case "students" -> query += " ORDER BY (SELECT COUNT(course_id) FROM student_course WHERE course.id = course_id)";
        	}
        	if ("desc".equals(order) && !"none".equals(orderBy)) {
        		query += " DESC";
        	}
        	query += " LIMIT ?, ?";
        	statement = connection.prepareStatement(query);
        	int counter = 1;
        	statement.setInt(counter, student.getId());
        	++counter;
        	if (!"all".equals(topic)) {
        		statement.setString(counter, topic);
        		++counter;
        		statement.setString(counter, topic);
        		++counter;
        	}
        	if (!"all".equals(teacherLogin)) {
        		statement.setString(counter, teacherLogin);
        		++counter;
        	}
        	statement.setInt(counter, (page - 1) * Constants.RECORDS_PER_PAGE);
        	++counter;
        	statement.setInt(counter, Constants.RECORDS_PER_PAGE);
        	resultSet = statement.executeQuery();
        	while (resultSet.next()) {
        		Course course = new Course();
        		course.setId(resultSet.getInt(1));
        		course.setNameEn(resultSet.getString(2));
        		course.setNameUk(resultSet.getString(3));
        		course.setTopicEn(resultSet.getString(4));
        		course.setTopicUk(resultSet.getString(5));
        		course.setStartDate(((Date) resultSet.getObject(6)).toLocalDate());
        		course.setDurationInDays(resultSet.getInt(7));
        		course.setTeacher(UserDaoImpl.getInstance().getById(resultSet.getInt(8)));
        		course.setStudents(resultSet.getInt(9));
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
