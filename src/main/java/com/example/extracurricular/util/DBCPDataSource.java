package com.example.extracurricular.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class that provides connection pool from Apache Commons DBCP library.
 *
 * @author Yurii Khmil
 */
public final class DBCPDataSource {
    private static DBCPDataSource instance;
    private final BasicDataSource dataSource;

    private DBCPDataSource() {
        dataSource = new BasicDataSource();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("database.properties");
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxOpenPreparedStatements(100);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized DBCPDataSource getInstance() {
        if (instance == null) {
            instance = new DBCPDataSource();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
