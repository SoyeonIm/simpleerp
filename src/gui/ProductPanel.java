/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author soyeon
 */
import product.InventoryManager;
import product.Product;

import javax.swing.*;
import java.awt.*;

public class ProductPanel extends JFrame {
    private InventoryManager inventoryManager;
    private JTextArea displayArea;

    public ProductPanel(InventoryManager manager) {
        this.inventoryManager = manager;

        setTitle("Product Management");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Product"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("Product ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel qtyLabel = new JLabel("Quantity:");
        JLabel priceLabel = new JLabel("Price:");

        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField qtyField = new JTextField(10);
        JTextField priceField = new JTextField(10);

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; inputPanel.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; inputPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(qtyLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; inputPanel.add(qtyField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(priceLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; inputPanel.add(priceField, gbc);

        JButton addBtn = new JButton("Add Product");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        inputPanel.add(addBtn, gbc);

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Product List"));

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        addBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            int qty;
            double price;

            try {
                qty = Integer.parseInt(qtyField.getText().trim());
                price = Double.parseDouble(priceField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity or price format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!id.isEmpty() && !name.isEmpty()) {
                inventoryManager.addProduct(new Product(id, name, qty, price));
                refreshDisplay();
                idField.setText(""); nameField.setText(""); qtyField.setText(""); priceField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        refreshDisplay();
        setVisible(true);
    }

    private void refreshDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Product p : inventoryManager.getProducts().values()) {
            sb.append(String.format("%-8s | %-15s | Qty: %-4d | $%.2f\n",
                    p.getId(), p.getName(), p.getQuantity(), p.getPrice()));
        }
        displayArea.setText(sb.toString());
    }
}
