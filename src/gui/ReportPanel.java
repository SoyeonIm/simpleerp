/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author soyeon
 */

import employee.EmployeeManager;
import product.InventoryManager;

import javax.swing.*;

public class ReportPanel extends JFrame {
    public ReportPanel(EmployeeManager empManager, InventoryManager invManager) {
        setTitle("System Report");
        setSize(300, 150);
        setLocationRelativeTo(null);

        int empCount = empManager.getEmployees().size();
        int prodCount = invManager.getProducts().size();

        JLabel report = new JLabel("<html><h3>Total Employees: " + empCount +
                "<br>Total Products: " + prodCount + "</h3></html>", SwingConstants.CENTER);
        add(report);

        setVisible(true);
    }
}

