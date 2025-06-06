/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DatabaseManager;
import product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void insert(Product p) {
        String sql = "INSERT INTO products (id, name, quantity, price) VALUES (?, ?, ?, ?)";

        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            System.err.println("Failed to Connect DB");
            return;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getId());
            stmt.setString(2, p.getName());
            stmt.setInt(3, p.getQuantity());
            stmt.setDouble(4, p.getPrice());
            stmt.executeUpdate();
            System.out.println("✅ Product added to DB");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateQuantity(String id, int qty) {
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";

        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            System.err.println("Failed to Connect DB");
            return;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, qty);
            stmt.setString(2, id);
            stmt.executeUpdate();
            System.out.println("🔄 Product quantity updated");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM products WHERE id = ?";

        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            System.err.println("Failed to Conncet DB");
            return;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
            System.out.println("🗑️ Product deleted");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";

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
                int qty = rs.getInt("quantity");
                double price = rs.getDouble("price");
                list.add(new Product(id, name, qty, price));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
