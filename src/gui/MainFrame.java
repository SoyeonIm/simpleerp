package gui;

import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Simple ERP System - GUI");
    }

    private void initComponents() {
       
JTabbedPane tabbedPane = new JTabbedPane();
tabbedPane.addTab("Employees", new EmployeePanel());
tabbedPane.addTab("Products", new ProductPanel());
tabbedPane.addTab("Report", new ReportPanel()); 



        getContentPane().add(tabbedPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
    }

    public static void main(String[] args) {
        //use default look and feel
        
        //start with login window instead of main window
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
