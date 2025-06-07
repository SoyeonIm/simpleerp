package gui;

import dao.ProductDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Product;

//product management panel with better layout
public class ProductPanel extends JPanel {
    private ProductDAO dao;
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, nameField, qtyField, priceField;

    public ProductPanel() {
        dao = new ProductDAO();
        setupUI();
        loadProducts();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //table setup
        model = new DefaultTableModel(new Object[]{"ID", "Name", "Quantity", "Price"}, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(new Color(230, 230, 230));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        //input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        //input fields
        idField = new JTextField(10);
        nameField = new JTextField(15);
        qtyField = new JTextField(8);
        priceField = new JTextField(10);
        
        //add components to input panel
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(qtyField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1;
        inputPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(priceField, gbc);
        
        add(inputPanel, BorderLayout.NORTH);

        //button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Product");
        JButton updateButton = new JButton("Update Quantity");
        JButton removeButton = new JButton("Remove Product");
        
        //make buttons look better
        addButton.setBackground(new Color(144, 238, 144));
        updateButton.setBackground(new Color(255, 255, 224));
        removeButton.setBackground(new Color(255, 182, 193));
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        //add button action
        addButton.addActionListener(e -> addProduct());
        
        //update button action  
        updateButton.addActionListener(e -> updateQuantity());
        
        //remove button action
        removeButton.addActionListener(e -> removeProduct());
    }
    
    //add new product
    private void addProduct() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String qtyStr = qtyField.getText().trim();
        String priceStr = priceField.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID and Name are required");
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyStr);
            double price = Double.parseDouble(priceStr);

            Product p = new Product(Integer.parseInt(id), name, quantity, price);
            dao.insert(p);
            clearFields();
            loadProducts();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity and Price must be numbers");
        }
    }
    
    //update selected product quantity
    private void updateQuantity() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product first");
            return;
        }
        
        String id = model.getValueAt(selected, 0).toString();
        String qtyStr = JOptionPane.showInputDialog(this, "Enter new quantity:");
        
        if (qtyStr != null) {
            try {
                int newQty = Integer.parseInt(qtyStr);
                dao.updateQuantity(id, newQty);
                loadProducts();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number");
            }
        }
    }
    
    //remove selected product
    private void removeProduct() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product first");
            return;
        }
        
        String id = model.getValueAt(selected, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this product?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(id);
            loadProducts();
        }
    }
    
    //clear input fields
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        qtyField.setText("");
        priceField.setText("");
    }

    //load all products from database into table
    private void loadProducts() {
        model.setRowCount(0);
        List<Product> products = dao.getAll();

        for (Product p : products) {
            model.addRow(new Object[]{
                p.getId(), p.getName(), p.getQuantity(), p.getPrice()
            });
        }
    }
}

