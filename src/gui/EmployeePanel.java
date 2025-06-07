package gui;

import dao.EmployeeDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Employee;

public class EmployeePanel extends JPanel {
    private EmployeeDAO dao;
    private JTable table;
    private DefaultTableModel model;

    public EmployeePanel() {
        dao = new EmployeeDAO();
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"ID", "Name", "Department"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JTextField nameField = new JTextField(10);
        JTextField deptField = new JTextField(10);
        JButton addButton = new JButton("Add");

        controlPanel.add(new JLabel("Name:"));
        controlPanel.add(nameField);
        controlPanel.add(new JLabel("Department:"));
        controlPanel.add(deptField);
        controlPanel.add(addButton);
        add(controlPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String dept = deptField.getText().trim();

            if (!name.isEmpty() && !dept.isEmpty()) {
                int id = (int)(System.currentTimeMillis() % 100000); // simple id generation
                Employee emp = new Employee(id, name, dept);
                dao.insert(emp);     // store in database
                loadEmployees();   // refresh
                nameField.setText("");
                deptField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Both fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadEmployees();  
    }

    private void loadEmployees() {
        model.setRowCount(0);
        List<Employee> employees = dao.getAll();  // from our database
        for (Employee emp : employees) {
            model.addRow(new Object[]{emp.getId(), emp.getName(), emp.getDepartment()});
        }
    }
}
