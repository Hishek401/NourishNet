package com.NourishNet.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConfig - Database Configuration Class
 * 
 * This class is responsible for creating a connection to our MySQL database.
 * Every time a Servlet needs to talk to the database, it calls DBConfig.getConnection().
 */
public class DBConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/nourishnet_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * This method creates and returns a connection to the database.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found! Did you add the JAR to WEB-INF/lib?");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
