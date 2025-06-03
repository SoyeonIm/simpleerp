/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author soyeon
 */
import employee.Employee;
import employee.EmployeeManager;

import javax.swing.*;
import java.awt.*;

public class EmployeePanel extends JFrame {
    private EmployeeManager employeeManager;
    private JTextArea displayArea;

    public EmployeePanel(EmployeeManager manager) {
        this.employeeManager = manager;

        setTitle("Employee Management");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField deptField = new JTextField();

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Department:"));
        inputPanel.add(deptField);

        JButton addBtn = new JButton("Add Employee");
        addBtn.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String dept = deptField.getText();
            if (!id.isEmpty() && !name.isEmpty() && !dept.isEmpty()) {
                employeeManager.addEmployee(new Employee(id, name, dept));
                refreshDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
            }
        });

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(addBtn, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        refreshDisplay();
        setVisible(true);
    }

    private void refreshDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Employee e : employeeManager.getEmployees()) {
            sb.append(e.id).append(" - ").append(e.name).append(" (").append(e.getDepartment()).append(")\n");
        }
        displayArea.setText(sb.toString());
    }
}

