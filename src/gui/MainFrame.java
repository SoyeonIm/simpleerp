/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author soyeon
 */

import javax.swing.*;
import java.awt.*;
import employee.EmployeeManager;
import product.InventoryManager;
import report.ReportGenerator;

public class MainFrame extends JFrame{
    
    private EmployeeManager employeeManager;
    private InventoryManager inventoryManager;
    private ReportGenerator reportGenerator;
    
    public MainFrame() {
        employeeManager = new EmployeeManager();
        inventoryManager = new InventoryManager();
        reportGenerator = new ReportGenerator();
        
        setTitle("Simple ERP System");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4,1));
        
        JButton employeeBtn = new JButton("Manage Employees");
        JButton productBtn = new JButton("Manage Products");
        JButton reportBtn = new JButton("Generate Report");
        JButton exitBtn = new JButton("Exit");

        employeeBtn.addActionListener(e -> new EmployeePanel(employeeManager));
        productBtn.addActionListener(e -> new ProductPanel(inventoryManager));
        reportBtn.addActionListener(e -> new ReportPanel(employeeManager, inventoryManager));
        exitBtn.addActionListener(e -> System.exit(0));

        add(employeeBtn);
        add(productBtn);
        add(reportBtn);
        add(exitBtn);
        
        setVisible(true);
        
        
    }
    
    public static void main(String[] args) {
        new MainFrame();
    }
    
}
