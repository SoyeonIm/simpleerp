/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:derby:simpleerpdb;create=true";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("✅ Connected to Derby DB!");
                initializeTables(connection);  // 여기서 connection 사용
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void initializeTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement(); 

        try {
            stmt.executeUpdate("""
                CREATE TABLE employees (
                    id VARCHAR(10) PRIMARY KEY,
                    name VARCHAR(100),
                    department VARCHAR(100)
                )
            """);
            System.out.println("🟢 EMPLOYEES table created.");
        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) throw e;
        }

        try {
            stmt.executeUpdate("""
                CREATE TABLE products (
                    id VARCHAR(10) PRIMARY KEY,
                    name VARCHAR(100),
                    quantity INT,
                    price DOUBLE
                )
            """);
            System.out.println("🟢 PRODUCTS table created.");
        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) throw e;
        }

        stmt.close(); // OK
     
    }
}

