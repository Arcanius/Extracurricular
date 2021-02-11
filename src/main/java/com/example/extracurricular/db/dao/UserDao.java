package com.example.extracurricular.db.dao;

import com.example.extracurricular.db.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * DAO interface for User entity.
 *
 * @author Yurii Khmil
 */
public interface UserDao {
    void save(User user) throws SQLException;
    
    void update(User user) throws SQLException;

    List<User> getAll() throws SQLException;
    
    User getById(int id) throws SQLException;

    Map<String, String> validateForLogin(User user) throws SQLException;

    Map<String, String> validateForRegistration(User user, String confirmPassword) throws SQLException;
}
