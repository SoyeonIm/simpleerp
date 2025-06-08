package gui;

import db.DatabaseManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Simple ERP System - GUI");
        setupCloseOperation();
    }
    
    private void setupCloseOperation() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DatabaseManager.getInstance().closeDatabase();
                System.exit(0);
            }
        });
    }

    private void initComponents() {
       
JTabbedPane tabbedPane = new JTabbedPane();
tabbedPane.addTab("Employees", new EmployeePanel());
tabbedPane.addTab("Products", new ProductPanel());
tabbedPane.addTab("Report", new ReportPanel()); 



        getContentPane().add(tabbedPane);
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
