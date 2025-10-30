package com.rydon.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles the MySQL database connection using a Singleton pattern.
 * This class has one responsibility: managing the DB connection.
 */
public class MySQLConnection {

    // --- !! IMPORTANT !! ---
    // Update these values to match your local MySQL setup
    private static final String URL = "jdbc:mysql://localhost:3306/rydondb";
    private static final String USERNAME = "root"; // Or your MySQL username
    private static final String PASSWORD = "pwd@admin"; // Your MySQL password
    // -------------------------

    private static Connection connection = null;

    /**
     * Private constructor to prevent instantiation.
     */
    private MySQLConnection() {
    }

    /**
     * Gets the single instance of the database connection.
     *
     * @return The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Maven automatically puts the driver where this code can find it
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Create the connection
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL JDBC Driver not found!");
                e.printStackTrace();
                throw new SQLException("JDBC Driver not found", e);
            }
        }
        return connection;
    }
}