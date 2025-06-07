/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//singleton pattern for database manager
public class DatabaseManager {
    
    private static DatabaseManager instance;
    private static final String DB_URL = "jdbc:derby:simpleerpdb;create=true";
    private Connection connection;

    //private constructor so no one can make new objects
    private DatabaseManager() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("database connected");
            setupTables();
        } catch (Exception e) {
            System.out.println("database connection failed");
            e.printStackTrace();
        }
    }

    //get the only instance
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    //get connection for dao classes
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException e) {
            System.out.println("database connection failed, retrying...");
            try {
                //try with different database name if locked
                String retryUrl = "jdbc:derby:simpleerpdb_backup;create=true";
                connection = DriverManager.getConnection(retryUrl);
                System.out.println("connected to backup database");
                setupTables(); //setup tables for backup database too
            } catch (SQLException ex) {
                System.out.println("all database connections failed");
                ex.printStackTrace();
            }
        }
        return connection;
    }
    
    //setup tables when first time run
    private void setupTables() {
        try {
            Statement stmt = connection.createStatement();
            
            //create employees table
            try {
                stmt.executeUpdate("CREATE TABLE employees (id VARCHAR(10) PRIMARY KEY, name VARCHAR(100), department VARCHAR(100))");
                System.out.println("employees table created");
            } catch (SQLException e) {
                //table already exists, thats fine
            }

            //create products table  
            try {
                stmt.executeUpdate("CREATE TABLE products (id VARCHAR(10) PRIMARY KEY, name VARCHAR(100), quantity INT, price DOUBLE)");
                System.out.println("products table created");
            } catch (SQLException e) {
                //table already exists, thats fine
            }
            
            //create users table for login
            try {
                stmt.executeUpdate("CREATE TABLE users (username VARCHAR(50) PRIMARY KEY, password VARCHAR(100))");
                System.out.println("users table created");
                
                //add default admin user
                try {
                    stmt.executeUpdate("INSERT INTO users (username, password) VALUES ('admin', 'admin123')");
                    System.out.println("default admin user created");
                } catch (SQLException ex) {
                    //user already exists, thats fine
                }
                
            } catch (SQLException e) {
                //table already exists, thats fine
            }

            stmt.close();// OK
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

