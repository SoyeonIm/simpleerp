package dao;

import db.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Employee;
import utils.ErrorHandler;

//data access object for employees
public class EmployeeDAO {

    public void insert(Employee e) {
        String sql = "INSERT INTO employees (id, name, department) VALUES (?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();
        
        try {
            // Enable auto-commit if not already enabled
            if (!conn.getAutoCommit()) {
                conn.setAutoCommit(true);
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, e.getId());
            stmt.setString(2, e.getName());
            stmt.setString(3, e.getDepartment());
            int result = stmt.executeUpdate();
            
            // Ensure transaction is committed
            if (!conn.getAutoCommit()) {
                conn.commit();
            }
            
            stmt.close();
            System.out.println("employee added successfully");
        } catch (SQLException ex) {
            ErrorHandler.handleDatabaseError(ex, "employee insert");
            rollbackTransaction(conn);
        }
    }

    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        Connection conn = DatabaseManager.getInstance().getConnection();
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String dept = rs.getString("department");
                employees.add(new Employee(Integer.parseInt(id), name, dept));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ErrorHandler.handleDatabaseError(ex, "employee select");
        }
        return employees;
    }
    
    private void rollbackTransaction(Connection conn) {
        try {
            if (!conn.getAutoCommit()) {
                conn.rollback();
            }
        } catch (SQLException ex) {
            ErrorHandler.handleDatabaseError(ex, "transaction rollback");
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        
        try {
            // Enable auto-commit if not already enabled
            if (!conn.getAutoCommit()) {
                conn.setAutoCommit(true);
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            int result = stmt.executeUpdate();
            
            // Ensure transaction is committed
            if (!conn.getAutoCommit()) {
                conn.commit();
            }
            
            stmt.close();
            System.out.println("employee deleted successfully");
        } catch (SQLException ex) {
            ErrorHandler.handleDatabaseError(ex, "employee delete");
            rollbackTransaction(conn);
        }
    }
}
