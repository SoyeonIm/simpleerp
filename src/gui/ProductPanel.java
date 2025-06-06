package gui;

import dao.ProductDAO;
import product.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductPanel extends JPanel {
    private ProductDAO dao;
    private JTable table;
    private DefaultTableModel model;

    public ProductPanel() {
        dao = new ProductDAO();
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"ID", "Name", "Quantity", "Price"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(10);
        JTextField qtyField = new JTextField(5);
        JTextField priceField = new JTextField(7);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update Quantity");
        JButton removeButton = new JButton("Remove");

        controlPanel.add(new JLabel("ID:"));
        controlPanel.add(idField);
        controlPanel.add(new JLabel("Name:"));
        controlPanel.add(nameField);
        controlPanel.add(new JLabel("Qty:"));
        controlPanel.add(qtyField);
        controlPanel.add(new JLabel("Price:"));
        controlPanel.add(priceField);
        controlPanel.add(addButton);
        controlPanel.add(updateButton);
        controlPanel.add(removeButton);
        add(controlPanel, BorderLayout.SOUTH);

        // Add product
        addButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String qtyStr = qtyField.getText().trim();
            String priceStr = priceField.getText().trim();

            try {
                int quantity = Integer.parseInt(qtyStr);
                double price = Double.parseDouble(priceStr);

                if (!id.isEmpty() && !name.isEmpty()) {
                    Product p = new Product(id, name, quantity, price);
                    dao.insert(p);  // ✅ DB 저장
                    loadProducts();
                    idField.setText("");
                    nameField.setText("");
                    qtyField.setText("");
                    priceField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "ID and Name are required.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity and Price must be numeric.");
            }
        });

        // Update product quantity
        updateButton.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                String id = model.getValueAt(selected, 0).toString();
                String qtyStr = JOptionPane.showInputDialog(this, "New quantity:");

                try {
                    int newQty = Integer.parseInt(qtyStr);
                    dao.updateQuantity(id, newQty);  // ✅ DB 업데이트
                    loadProducts();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a product to update.");
            }
        });

        // Remove product
        removeButton.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                String id = model.getValueAt(selected, 0).toString();
                dao.delete(id);  // ✅ DB 삭제
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Select a product to remove.");
            }
        });

        loadProducts();
    }

    private void loadProducts() {
        model.setRowCount(0);
        List<Product> products = dao.getAll();  // ✅ DB에서 전체 제품 불러오기

        for (Product p : products) {
            model.addRow(new Object[]{
                    p.getId(), p.getName(), p.getQuantity(), p.getPrice()
            });
        }
    }
}

