package gui;

import dao.ProductDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Product;
import utils.ResourceManager;

//product management panel with better layout
public class ProductPanel extends JPanel {
    private ProductDAO dao;
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, nameField, qtyField, priceField;
    private JTextField searchField;

    public ProductPanel() {
        dao = new ProductDAO();
        setupUI();
        loadProducts();
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setMinimumSize(new Dimension(800, 600));
        setPreferredSize(new Dimension(1000, 700));
        
        //use background image
        ImageIcon bgImage = ResourceManager.getBackgroundImage("panel_bg.png");
        if (bgImage != null) {
            setOpaque(false);
        } else {
            setBackground(ResourceManager.Colors.BACKGROUND);
        }

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createInputPanel(), BorderLayout.SOUTH);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon bgImage = ResourceManager.getBackgroundImage("panel_bg.png");
        if (bgImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setBackground(ResourceManager.Colors.BACKGROUND);

        JLabel titleLabel = new JLabel("Product Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(ResourceManager.Colors.TEXT_PRIMARY);
        titleLabel.setIcon(ResourceManager.getResizedIcon("product.png", 56, 56));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(ResourceManager.Colors.BACKGROUND);
        
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ResourceManager.Colors.BORDER),
            new EmptyBorder(8, 12, 8, 12)
        ));
        
        JButton searchBtn = createStyledButton("Search", ResourceManager.Colors.PRIMARY, "search.png");
        JButton refreshBtn = createStyledButton("Refresh", ResourceManager.Colors.SUCCESS, "refresh.png");
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(refreshBtn);

        header.add(titleLabel, BorderLayout.WEST);
        header.add(searchPanel, BorderLayout.EAST);

        searchBtn.addActionListener(e -> searchProducts());
        refreshBtn.addActionListener(e -> loadProducts());

        return header;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ResourceManager.Colors.BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ResourceManager.Colors.BORDER),
            "Product List",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            ResourceManager.Colors.TEXT_PRIMARY
        ));

        model = new DefaultTableModel(new Object[]{"ID", "Product Name", "Quantity", "Price ($)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setSelectionBackground(ResourceManager.Colors.PRIMARY);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(ResourceManager.Colors.BORDER);
        
        table.getTableHeader().setBackground(ResourceManager.Colors.DEEP_BLUE);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(ResourceManager.Colors.BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createInputPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(ResourceManager.Colors.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ResourceManager.Colors.BORDER),
            "Product Details",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            ResourceManager.Colors.TEXT_PRIMARY
        ));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = createStyledTextField(10);
        nameField = createStyledTextField(20);
        qtyField = createStyledTextField(10);
        priceField = createStyledTextField(10);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(createLabel("Product ID:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        inputPanel.add(createLabel("Product Name:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(createLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(qtyField, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        inputPanel.add(createLabel("Price ($):"), gbc);
        gbc.gridx = 3;
        inputPanel.add(priceField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton addBtn = createStyledButton("Add Product", ResourceManager.Colors.PRIMARY, "add.png");
        JButton updateBtn = createStyledButton("Update", ResourceManager.Colors.ACCENT, "edit.png");
        JButton deleteBtn = createStyledButton("Delete", ResourceManager.Colors.DANGER, "delete..png");
        JButton clearBtn = createStyledButton("Clear", ResourceManager.Colors.TEXT_SECONDARY, "clear.png");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);

        addBtn.addActionListener(e -> addProduct());
        updateBtn.addActionListener(e -> updateProduct());
        deleteBtn.addActionListener(e -> deleteProduct());
        clearBtn.addActionListener(e -> clearFields());

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(ResourceManager.Colors.TEXT_PRIMARY);
        return label;
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ResourceManager.Colors.BORDER),
            new EmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 35));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor, String iconName) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 40));
        button.setMargin(new Insets(8, 12, 8, 12));
        
        // Add subtle border for better definition
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        
        if (iconName != null) {
            ImageIcon icon = ResourceManager.getResizedIcon(iconName, 20, 20);
            button.setIcon(icon);
            button.setIconTextGap(8);
        }

        Color originalColor = bgColor;
        Color hoverColor = bgColor.brighter();
        Color pressedColor = bgColor.darker();
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(pressedColor);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
        });

        return button;
    }

    private void addProduct() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String qtyStr = qtyField.getText().trim();
        String priceStr = priceField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || qtyStr.isEmpty() || priceStr.isEmpty()) {
            showMessage("Please fill in all fields", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int productId = Integer.parseInt(id);
            int quantity = Integer.parseInt(qtyStr);
            double price = Double.parseDouble(priceStr);

            Product product = new Product(productId, name, quantity, price);
            dao.insert(product);
            
            showMessage("Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            loadProducts();
            
        } catch (NumberFormatException ex) {
            showMessage("ID, Quantity and Price must be valid numbers", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            showMessage("Please select a product to update", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = model.getValueAt(selected, 0).toString();
        String qtyStr = JOptionPane.showInputDialog(this, "Enter new quantity:", "Update Quantity", JOptionPane.QUESTION_MESSAGE);

        if (qtyStr != null && !qtyStr.trim().isEmpty()) {
            try {
                int newQty = Integer.parseInt(qtyStr);
                dao.updateQuantity(id, newQty);
                showMessage("Quantity updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadProducts();
            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteProduct() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            showMessage("Please select a product to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = model.getValueAt(selected, 0).toString();
        String name = model.getValueAt(selected, 1).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete product: " + name + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(id);
            showMessage("Product deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadProducts();
        }
    }

    private void searchProducts() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            loadProducts();
            return;
        }

        model.setRowCount(0);
        List<Product> products = dao.getAll();

        for (Product p : products) {
            if (p.getName().toLowerCase().contains(searchText) || 
                String.valueOf(p.getId()).contains(searchText)) {
                model.addRow(new Object[]{
                    p.getId(), p.getName(), p.getQuantity(), String.format("%.2f", p.getPrice())
                });
            }
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        qtyField.setText("");
        priceField.setText("");
        searchField.setText("");
    }

    private void loadProducts() {
        model.setRowCount(0);
        List<Product> products = dao.getAll();

        for (Product p : products) {
            model.addRow(new Object[]{
                p.getId(), p.getName(), p.getQuantity(), String.format("%.2f", p.getPrice())
            });
        }
    }

    private void showMessage(String message, String title, int type) {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        switch (type) {
            case JOptionPane.ERROR_MESSAGE:
                CustomMessageDialog.showError(parentFrame, message, title);
                break;
            case JOptionPane.WARNING_MESSAGE:
                CustomMessageDialog.showWarning(parentFrame, message, title);
                break;
            case JOptionPane.INFORMATION_MESSAGE:
                CustomMessageDialog.showInfo(parentFrame, message, title);
                break;
            default:
                CustomMessageDialog.showInfo(parentFrame, message, title);
                break;
        }
    }
}

