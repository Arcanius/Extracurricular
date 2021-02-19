package com.example.extracurricular.db.dao;

import com.example.extracurricular.db.model.User;
import com.example.extracurricular.db.model.User.Role;
import com.example.extracurricular.util.Constants;
import com.example.extracurricular.util.DBCPDataSource;
import com.example.extracurricular.util.Security;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of UserDao interface.
 *
 * @author Yurii Khmil
 */
public final class UserDaoImpl implements UserDao {
    private static UserDao instance;
    private final DBCPDataSource dataSource;
    private static final Logger log = Logger.getLogger(UserDaoImpl.class);

    private UserDaoImpl() {
        dataSource = DBCPDataSource.getInstance();
    }

    public static synchronized UserDao getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public void save(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            String query = "INSERT INTO user (login, password, name_en, name_uk, role_id, banned) VALUES (?, ?, ?, ?, (SELECT id FROM role WHERE name = ?), ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, Security.sha512(user));
            statement.setString(3, user.getNameEn());
            statement.setString(4, user.getNameUk());
            statement.setString(5, user.getRole().name());
            statement.setBoolean(6, user.isBanned());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            log.info("User " + user + " saved in database");
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            close(connection, statement, resultSet);
        }
    }
    
    @Override
    public void update(User user) throws SQLException {
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "UPDATE user SET role_id = (SELECT id FROM role WHERE name = ?), banned = ? WHERE id = ?";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, user.getRole().name());
        	statement.setBoolean(2, user.isBanned());
        	statement.setInt(3, user.getId());
        	statement.executeUpdate();
        	log.info("User " + user + " updated in database");
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            String query = "SELECT id, login, password, name_en, name_uk, (SELECT name FROM role WHERE id = role_id), banned FROM user";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setNameEn(resultSet.getString(4));
                user.setNameUk(resultSet.getString(5));
                user.setRole(User.Role.valueOf(resultSet.getString(6)));
                user.setBanned(resultSet.getBoolean(7));
                users.add(user);
            }
            log.info("List of users provided by database");
            return users;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            close(connection, statement, resultSet);
        }
    }
    
    @Override
    public User getById(int id) throws SQLException {
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT login, password, name_en, name_uk, (SELECT name FROM role WHERE id = role_id), banned FROM user WHERE id = ?";
        	statement = connection.prepareStatement(query);
        	statement.setInt(1, id);
        	resultSet = statement.executeQuery();
        	User user = null;
        	if (resultSet.next()) {
        		user = new User();
        		user.setId(id);
        		user.setLogin(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setNameEn(resultSet.getString(3));
                user.setNameUk(resultSet.getString(4));
                user.setRole(User.Role.valueOf(resultSet.getString(5)));
                user.setBanned(resultSet.getBoolean(6));
        	}
        	log.info("User " + user + " provided by database");
        	return user;
        } catch (SQLException e) {
        	log.error(e.getMessage());
        	throw e;
        } finally {
        	close(connection, statement, resultSet);
        }
    }
    
    @Override
    public User getByLogin(String login) throws SQLException {
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT id, password, name_en, name_uk, (SELECT name FROM role WHERE id = role_id), banned FROM user WHERE login = ?";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, login);
        	resultSet = statement.executeQuery();
        	User user = null;
        	if (resultSet.next()) {
        		user = new User();
        		user.setId(resultSet.getInt(1));
        		user.setLogin(login);
                user.setPassword(resultSet.getString(2));
                user.setNameEn(resultSet.getString(3));
                user.setNameUk(resultSet.getString(4));
                user.setRole(User.Role.valueOf(resultSet.getString(5)));
                user.setBanned(resultSet.getBoolean(6));
        	}
        	log.info("User " + user + " provided by database");
        	return user;
        } catch (SQLException e) {
        	log.error(e.getMessage());
        	throw e;
        } finally {
        	close(connection, statement, resultSet);
        }
    }

    @Override
    public Map<String, String> validateForLogin(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Map<String, String> errors = new HashMap<>();
            connection = dataSource.getConnection();
            String query = "SELECT id, password, name_en, name_uk, (SELECT name FROM role WHERE id = role_id), banned FROM user WHERE login = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (Security.sha512(user).equals(resultSet.getString(2))) {
                    user.setId(resultSet.getInt(1));
                    user.setNameEn(resultSet.getString(3));
                    user.setNameUk(resultSet.getString(4));
                    user.setRole(User.Role.valueOf(resultSet.getString(5)));
                    user.setBanned(resultSet.getBoolean(6));
                } else {
                    errors.put("password.wrong", "error.password.wrong");
                }
            } else {
                errors.put("login.wrong", "error.login.wrong");
            }
            log.info("User " + user +  " validated for login. Result: " + errors.isEmpty());
            return errors;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public Map<String, String> validateForRegistration(User user, String confirmPassword) throws SQLException {
        Map<String, String> errors = new HashMap<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            String query = "SELECT login FROM user WHERE login = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                errors.put("login.error", "error.login.busy");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            close(connection, statement, resultSet);
        }
        if (user.getLogin().length() < Constants.LOGIN_MIN || user.getLogin().length() > Constants.LOGIN_MAX) {
            errors.put("login.error", "error.login.size");
        }
        if (!user.getLogin().matches("\\w+")) {
        	errors.put("login.error", "error.login.symbols");
        }
        if (user.getPassword().length() < Constants.PASSWORD_MIN || user.getPassword().length() > Constants.PASSWORD_MAX) {
            errors.put("password.error", "error.password.size");
        }
        if (!user.getPassword().matches("\\w+")) {
        	errors.put("password.error", "error.password.symbols");
        }
        if (!user.getPassword().equals(confirmPassword)) {
            errors.put("confirmPassword.error", "error.confirm_password.match");
        }
        if (user.getNameEn().length() < Constants.NAME_MIN || user.getNameEn().length() > Constants.NAME_MAX) {
            errors.put("nameEn.error", "error.name.size");
        }
        if (!user.getNameEn().matches("[A-Z][A-Za-z ]+[A-Za-z]")) {
        	errors.put("nameEn.error", "error.name.wrong");
        }
        if (user.getNameUk().length() < Constants.NAME_MIN || user.getNameUk().length() > Constants.NAME_MAX) {
            errors.put("nameUk.error", "error.name.size");
        }
        if (!user.getNameUk().matches("[А-ЩЬЮЯҐЄІЇ][А-ЩЬЮЯҐЄІЇа-щьюяґєії' ]+[А-ЩЬЮЯҐЄІЇа-щьюяґєії]")) {
        	errors.put("nameUk.error", "error.name.wrong");
        }
        log.info("User " + user + " validated for registration. Result: " + errors.isEmpty());
        return errors;
    }
    
    @Override
    public List<User> getAllByRole(Role role) throws SQLException {
    	List<User> users = new ArrayList<>();
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = dataSource.getConnection();
        	String query = "SELECT id, login, password, name_en, name_uk, banned FROM user WHERE role_id = (SELECT id FROM role WHERE name = ?)";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, role.name());
        	resultSet = statement.executeQuery();
        	while (resultSet.next()) {
        		User user = new User();
        		user.setId(resultSet.getInt(1));
        		user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setNameEn(resultSet.getString(4));
                user.setNameUk(resultSet.getString(5));
                user.setRole(role);
                user.setBanned(resultSet.getBoolean(6));
                users.add(user);
        	}
        	log.info("List of users provided by database");
        	return users;
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
