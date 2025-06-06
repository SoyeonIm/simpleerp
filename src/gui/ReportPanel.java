package gui;

import dao.EmployeeDAO;
import dao.ProductDAO;

import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {
    private EmployeeDAO employeeDAO;
    private ProductDAO productDAO;

    public ReportPanel() {
        employeeDAO = new EmployeeDAO();
        productDAO = new ProductDAO();

        int employeeCount = employeeDAO.getAll().size();
        int productCount = productDAO.getAll().size();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(30));
        JLabel empLabel = new JLabel("👤 Total Employees: " + employeeCount);
        empLabel.setFont(new Font("Arial", Font.BOLD, 18));
        empLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(empLabel);

        add(Box.createVerticalStrut(20));

        JLabel prodLabel = new JLabel("📦 Total Products: " + productCount);
        prodLabel.setFont(new Font("Arial", Font.BOLD, 18));
        prodLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(prodLabel);
    }
}
