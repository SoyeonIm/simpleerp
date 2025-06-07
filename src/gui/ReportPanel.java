package gui;

import dao.EmployeeDAO;
import dao.ProductDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Employee;
import model.Product;


//simple report panel showing basic stats
public class ReportPanel extends JPanel {
    private EmployeeDAO employeeDAO;
    private ProductDAO productDAO;
    private JTextArea reportArea;

    public ReportPanel() {
        employeeDAO = new EmployeeDAO();
        productDAO = new ProductDAO();
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //title
        JLabel titleLabel = new JLabel("System Reports", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);
        
        //report text area
        reportArea = new JTextArea(15, 40);
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setBackground(new Color(248, 248, 248));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        add(scrollPane, BorderLayout.CENTER);
        
        //button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Refresh Report");
        JButton clearButton = new JButton("Clear");
        
        refreshButton.setBackground(new Color(173, 216, 230));
        clearButton.setBackground(new Color(255, 228, 225));
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        //button actions
        refreshButton.addActionListener(e -> generateReport());
        clearButton.addActionListener(e -> reportArea.setText(""));
        
        //generate initial report
        generateReport();
    }
    
    //generate simple report with basic stats
    private void generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== SIMPLE ERP SYSTEM REPORT ===\n\n");
        
        //product stats
        List<Product> products = productDAO.getAll();
        report.append("PRODUCT SUMMARY:\n");
        report.append("Total Products: ").append(products.size()).append("\n");
        
        if (!products.isEmpty()) {
            int totalQty = 0;
            double totalValue = 0;
            for (Product p : products) {
                totalQty += p.getQuantity();
                totalValue += p.getQuantity() * p.getPrice();
            }
            report.append("Total Inventory Quantity: ").append(totalQty).append("\n");
            report.append("Total Inventory Value: $").append(String.format("%.2f", totalValue)).append("\n");
        }
        
        report.append("\nPRODUCT LIST:\n");
        for (Product p : products) {
            report.append("- ").append(p.getId()).append(": ").append(p.getName())
                  .append(" (Qty: ").append(p.getQuantity()).append(", Price: $")
                  .append(p.getPrice()).append(")\n");
        }
        
        //employee stats
        List<Employee> employees = employeeDAO.getAll();
        report.append("\n\nEMPLOYEE SUMMARY:\n");
        report.append("Total Employees: ").append(employees.size()).append("\n");
        
        report.append("\nEMPLOYEE LIST:\n");
        for (Employee e : employees) {
            report.append("- ").append(e.getId()).append(": ").append(e.getName())
                  .append(" (Dept: ").append(e.getDepartment()).append(")\n");
        }
        
        report.append("\n=== END OF REPORT ===");
        reportArea.setText(report.toString());
    }
}
