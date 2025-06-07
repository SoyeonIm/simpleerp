package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

//simple login window
public class LoginFrame extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public LoginFrame() {
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Simple ERP - Login");
        setSize(300, 200); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);
        
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        
        panel.add(loginButton);
        panel.add(registerButton);
        
        //empty row for spacing
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        
        add(panel);
        
        //login button action
        loginButton.addActionListener(this::loginAction);
        
        //register button action
        registerButton.addActionListener(this::registerAction);
        
        //enter key on password field = login
        passwordField.addActionListener(this::loginAction);
    }
    
    private void loginAction(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password");
            return;
        }
        
        //simple hardcoded login for now
        if ((username.equals("admin") && password.equals("admin123")) || 
            (username.equals("test") && password.equals("test"))) {
            System.out.println("login successful");
            dispose(); //close login window
            SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password\nTry: admin/admin123 or test/test");
            passwordField.setText("");
        }
    }
    
    private void registerAction(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password");
            return;
        }
        
        //simple registration simulation for now
        JOptionPane.showMessageDialog(this, "Registration feature temporarily disabled.\nUse: admin/admin123 or test/test to login.");
        passwordField.setText("");
    }
    
    public static void main(String[] args) {
        //use default swing look and feel
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
} 