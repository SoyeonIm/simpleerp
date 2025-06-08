package dao;

import db.DatabaseManager;
import java.sql.*;

//dao for user login stuff
public class UserDAO {
    
    //check if user exists and password is correct
    public boolean login(String username, String password) {
        //check for empty credentials first
        if(username == null || username.trim().isEmpty() || 
           password == null || password.trim().isEmpty()) {
            return false;
        }
        
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            boolean found = rs.next();
            rs.close();
            stmt.close();
            return found;
        } catch (SQLException ex) {
            System.out.println("login error: " + ex.getMessage());
            return false;
        }
    }
    
    //add new user - simple registration
    public boolean register(String username, String password) {
        //check for empty credentials first
        if(username == null || username.trim().isEmpty() || 
           password == null || password.trim().isEmpty()) {
            return false;
        }
        
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            int result = stmt.executeUpdate();
            
            //ensure transaction is committed
            if (!conn.getAutoCommit()) {
                conn.commit();
            }
            
            stmt.close();
            System.out.println("user registered successfully: " + username + " (rows: " + result + ")");
            return true;
        } catch (SQLException ex) {
            System.out.println("registration error: " + ex.getMessage());
            try {
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("rollback failed: " + rollbackEx.getMessage());
            }
            return false;
        }
    }
} 