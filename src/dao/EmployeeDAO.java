/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DatabaseManager;
import employee.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public void insert(Employee e) {
        String sql = "INSERT INTO employees (id, name, department) VALUES (?, ?, ?)";

        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            System.err.println("Failed to Conncet DB");
            return;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getId());
            stmt.setString(2, e.getName());
            stmt.setString(3, e.getDepartment());
            stmt.executeUpdate();
            System.out.println("✅ Employee added to DB");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            System.err.println("Failed to Conncet DB");
            return list;
        }

        try (
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String dept = rs.getString("department");
                list.add(new Employee(id, name, dept));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public void delete(String id) {
        String sql = "DELETE FROM employees WHERE id = ?";

        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            System.err.println("Failed to conncet DB");
            return;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
            System.out.println("🗑️ Employee deleted from DB");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
