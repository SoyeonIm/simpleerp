package dao;

import db.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Product;

//data access object for products
public class ProductDAO {

    public void insert(Product p) {
        String sql = "INSERT INTO products (id, name, quantity, price) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try {
            //enable auto-commit if not already enabled
            if (!conn.getAutoCommit()) {
                conn.setAutoCommit(true);
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getName());
            stmt.setInt(3, p.getQuantity());
            stmt.setDouble(4, p.getPrice());
            int result = stmt.executeUpdate();
            
            //ensure transaction is committed
            if (!conn.getAutoCommit()) {
                conn.commit();
            }
            
            stmt.close();
            System.out.println("product added successfully");
        } catch (SQLException ex) {
            System.out.println("error adding product: " + ex.getMessage());
            try {
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("rollback failed: " + rollbackEx.getMessage());
            }
        }
    }

    public void updateQuantity(String id, int qty) {
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try {
            //nable auto-commit if not already enabled
            if (!conn.getAutoCommit()) {
                conn.setAutoCommit(true);
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qty);
            stmt.setString(2, id);
            int result = stmt.executeUpdate();
            
            //ensure transaction is committed
            if (!conn.getAutoCommit()) {
                conn.commit();
            }
            
            stmt.close();
            System.out.println("product quantity updated (rows affected: " + result + ")");
        } catch (SQLException ex) {
            System.out.println("error updating product: " + ex.getMessage());
            try {
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("rollback failed: " + rollbackEx.getMessage());
            }
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM products WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try {
            //enable auto-commit if not already enabled
            if (!conn.getAutoCommit()) {
                conn.setAutoCommit(true);
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            int result = stmt.executeUpdate();
            
            //ensure transaction is committed
            if (!conn.getAutoCommit()) {
                conn.commit();
            }
            
            stmt.close();
            System.out.println("product deleted (rows affected: " + result + ")");
        } catch (SQLException ex) {
            System.out.println("error deleting product: " + ex.getMessage());
            try {
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("rollback failed: " + rollbackEx.getMessage());
            }
        }
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                int qty = rs.getInt("quantity");
                double price = rs.getDouble("price");
                products.add(new Product(Integer.parseInt(id), name, qty, price));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("error getting products: " + ex.getMessage());
        }
        return products;
    }
    
    public void deleteMultiple(String[] ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        
        Connection conn = DatabaseManager.getInstance().getConnection();
        String sql = "DELETE FROM products WHERE id = ?";
        
        try {
            if (!conn.getAutoCommit()) {
                conn.setAutoCommit(true);
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            int count = 0;
            
            for (String id : ids) {
                if (id != null && !id.trim().isEmpty()) {
                    stmt.setString(1, id.trim());
                    stmt.addBatch();
                    count++;
                }
            }
            
            if (count > 0) {
                int[] results = stmt.executeBatch();
                System.out.println("deleted " + count + " products");
            }
            
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("error in batch delete: " + ex.getMessage());
            try {
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("rollback failed: " + rollbackEx.getMessage());
            }
        }
    }
}
