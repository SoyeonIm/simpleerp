package gui;

import dao.EmployeeDAO;
import dao.ProductDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.Employee;
import model.Product;
import utils.ResourceManager;

public class ReportPanel extends JPanel {
    private EmployeeDAO employeeDAO;
    private ProductDAO productDAO;
    private JTextArea reportArea;
    private JLabel totalProductsLabel, totalEmployeesLabel, totalValueLabel;

    public ReportPanel() {
        employeeDAO = new EmployeeDAO();
        productDAO = new ProductDAO();
        setupUI();
        generateReport();
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
        add(createStatsPanel(), BorderLayout.CENTER);
        add(createReportPanel(), BorderLayout.SOUTH);
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

        JLabel titleLabel = new JLabel("System Reports & Analytics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(ResourceManager.Colors.TEXT_PRIMARY);
        titleLabel.setIcon(ResourceManager.getResizedIcon("repoet.png", 56, 56));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(ResourceManager.Colors.BACKGROUND);
        
        JButton refreshBtn = createStyledButton("Refresh", ResourceManager.Colors.SUCCESS, "refresh.png");
        JButton clearBtn = createStyledButton("Clear", ResourceManager.Colors.TEXT_SECONDARY, "clear.png");
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(clearBtn);

        header.add(titleLabel, BorderLayout.WEST);
        header.add(buttonPanel, BorderLayout.EAST);

        refreshBtn.addActionListener(e -> generateReport());
        clearBtn.addActionListener(e -> reportArea.setText(""));

        return header;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setBackground(ResourceManager.Colors.BACKGROUND);
        statsPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        statsPanel.add(createStatCard("Total Products", "0", ResourceManager.Colors.PRIMARY, "product.png"));
        statsPanel.add(createStatCard("Total Employees", "0", ResourceManager.Colors.ACCENT, "employee.png"));
        statsPanel.add(createStatCard("Inventory Value", "$0.00", ResourceManager.Colors.SUCCESS, "value.png"));

        return statsPanel;
    }

    private JPanel createStatCard(String title, String value, Color color, String iconName) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setOpaque(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(ResourceManager.Colors.TEXT_SECONDARY);

        if (iconName != null) {
            JLabel iconLabel = new JLabel(ResourceManager.getResizedIcon(iconName, 32, 32));
            topPanel.add(iconLabel, BorderLayout.EAST);
        }

        topPanel.add(titleLabel, BorderLayout.WEST);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        if (title.equals("Total Products")) {
            totalProductsLabel = valueLabel;
        } else if (title.equals("Total Employees")) {
            totalEmployeesLabel = valueLabel;
        } else if (title.equals("Inventory Value")) {
            totalValueLabel = valueLabel;
        }

        card.add(topPanel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ResourceManager.Colors.BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ResourceManager.Colors.BORDER),
            "Detailed Report",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            ResourceManager.Colors.TEXT_PRIMARY
        ));

        reportArea = new JTextArea(12, 50);
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        reportArea.setBackground(Color.WHITE);
        reportArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        reportArea.setLineWrap(true);
        reportArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(ResourceManager.Colors.BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
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

    private void generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== SIMPLE ERP SYSTEM REPORT ===\n");
        report.append("Generated on: ").append(new java.util.Date()).append("\n\n");
        
        List<Product> products = productDAO.getAll();
        List<Employee> employees = employeeDAO.getAll();
        
        updateStatCards(products, employees);
        
        report.append("PRODUCT ANALYSIS:\n");
        report.append("================\n");
        report.append("Total Products: ").append(products.size()).append("\n");
        
        if (!products.isEmpty()) {
            int totalQty = 0;
            double totalValue = 0;
            Product mostExpensive = products.get(0);
            Product leastStock = products.get(0);
            
            for (Product p : products) {
                totalQty += p.getQuantity();
                totalValue += p.getQuantity() * p.getPrice();
                
                if (p.getPrice() > mostExpensive.getPrice()) {
                    mostExpensive = p;
                }
                if (p.getQuantity() < leastStock.getQuantity()) {
                    leastStock = p;
                }
            }
            
            report.append("Total Inventory Quantity: ").append(totalQty).append(" units\n");
            report.append("Total Inventory Value: $").append(String.format("%.2f", totalValue)).append("\n");
            report.append("Average Product Price: $").append(String.format("%.2f", totalValue / totalQty)).append("\n");
            report.append("Most Expensive Product: ").append(mostExpensive.getName())
                  .append(" ($").append(mostExpensive.getPrice()).append(")\n");
            report.append("Lowest Stock Product: ").append(leastStock.getName())
                  .append(" (").append(leastStock.getQuantity()).append(" units)\n");
        }
        
        report.append("\nPRODUCT INVENTORY:\n");
        for (Product p : products) {
            String status = p.getQuantity() < 10 ? " [LOW STOCK]" : "";
            report.append("• ").append(p.getName())
                  .append(" - Qty: ").append(p.getQuantity())
                  .append(", Price: $").append(String.format("%.2f", p.getPrice()))
                  .append(status).append("\n");
        }
        
        report.append("\n\nEMPLOYEE ANALYSIS:\n");
        report.append("==================\n");
        report.append("Total Employees: ").append(employees.size()).append("\n");
        
        if (!employees.isEmpty()) {
            java.util.Map<String, Integer> deptCount = new java.util.HashMap<>();
            for (Employee e : employees) {
                deptCount.put(e.getDepartment(), deptCount.getOrDefault(e.getDepartment(), 0) + 1);
            }
            
            report.append("Departments: ").append(deptCount.size()).append("\n");
            report.append("Department Distribution:\n");
            for (java.util.Map.Entry<String, Integer> entry : deptCount.entrySet()) {
                report.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" employees\n");
            }
        }
        
        report.append("\nEMPLOYEE DIRECTORY:\n");
        for (Employee e : employees) {
            report.append("• ").append(e.getName())
                  .append(" (ID: ").append(e.getId())
                  .append(", Dept: ").append(e.getDepartment()).append(")\n");
        }
        
        report.append("\n=== END OF REPORT ===");
        reportArea.setText(report.toString());
        reportArea.setCaretPosition(0);
    }

    private void updateStatCards(List<Product> products, List<Employee> employees) {
        totalProductsLabel.setText(String.valueOf(products.size()));
        totalEmployeesLabel.setText(String.valueOf(employees.size()));
        
        double totalValue = 0;
        for (Product p : products) {
            totalValue += p.getQuantity() * p.getPrice();
        }
        totalValueLabel.setText("$" + String.format("%.2f", totalValue));
    }
}
