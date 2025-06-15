package gui;

import dao.UserDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import utils.ResourceManager;

public class RegistrationFrame extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private UserDAO userDAO;
    
    public RegistrationFrame() {
        userDAO = new UserDAO();
        setupUI();
    }
    
    private void setupUI() {
        setTitle("SimpleERP Registration");
        setSize(450, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        //main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                //gradient background
                Color color1 = ResourceManager.Colors.DEEP_BLUE;
                Color color2 = ResourceManager.Colors.LIGHT_GRAY;
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        //registration card panel
        JPanel registerCard = createRegisterCard();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerCard, gbc);
        
        add(mainPanel);
    }
    
    private JPanel createRegisterCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(ResourceManager.Colors.CARD_BG);
        card.setBorder(new EmptyBorder(40, 40, 40, 40));
        card.setPreferredSize(new Dimension(350, 530));
        card.setOpaque(false);
        
        //title
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(ResourceManager.Colors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Join SimpleERP today");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(ResourceManager.Colors.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(ResourceManager.Colors.CARD_BG);
        formPanel.setBorder(new EmptyBorder(30, 0, 30, 0));
        
        //username field
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userLabel.setForeground(ResourceManager.Colors.TEXT_PRIMARY);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(270, 40));
        usernameField.setMaximumSize(new Dimension(270, 40));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ResourceManager.Colors.BORDER),
            new EmptyBorder(8, 12, 8, 12)
        ));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //password field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passLabel.setForeground(ResourceManager.Colors.TEXT_PRIMARY);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
        
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(270, 40));
        passwordField.setMaximumSize(new Dimension(270, 40));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ResourceManager.Colors.BORDER),
            new EmptyBorder(8, 12, 8, 12)
        ));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //confirm password field
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        confirmLabel.setForeground(ResourceManager.Colors.TEXT_PRIMARY);
        confirmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(270, 40));
        confirmPasswordField.setMaximumSize(new Dimension(270, 40));
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ResourceManager.Colors.BORDER),
            new EmptyBorder(8, 12, 8, 12)
        ));
        confirmPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //buttons
        JButton registerButton = createStyledButton("Create Account", ResourceManager.Colors.PRIMARY);
        registerButton.setIcon(ResourceManager.getResizedIcon("user.png", 16, 16));
        
        JButton backButton = createStyledButton("Back to Login", ResourceManager.Colors.ACCENT);
        
        //add components to form
        formPanel.add(userLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(usernameField);
        formPanel.add(passLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(passwordField);
        formPanel.add(confirmLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(confirmPasswordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        formPanel.add(registerButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(backButton);
        
        //add to card
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(subtitleLabel);
        card.add(formPanel);
        
        //add action listeners
        registerButton.addActionListener(this::registerAction);
        backButton.addActionListener(this::backToLoginAction);
        confirmPasswordField.addActionListener(this::registerAction);
        
        return card;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(270, 45));
        button.setMaximumSize(new Dimension(270, 45));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //hover effect
        Color originalColor = bgColor;
        Color hoverColor = bgColor.darker();
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    private void registerAction(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        //validation
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Please fill in all fields", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (username.length() < 3) {
            showMessage("Username must be at least 3 characters long", "Invalid Username", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (password.length() < 4) {
            showMessage("Password must be at least 4 characters long", "Invalid Password", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showMessage("Passwords do not match", "Password Mismatch", JOptionPane.WARNING_MESSAGE);
            confirmPasswordField.setText("");
            confirmPasswordField.requestFocus();
            return;
        }
        
        //attempt registration
        if (userDAO.register(username, password)) {
            JOptionPane.showMessageDialog(this, "Account created successfully! You can now login.", "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
            backToLoginAction(null);
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Username may already exist.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            usernameField.requestFocus();
        }
    }
    
    private void backToLoginAction(ActionEvent e) {
        dispose();
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
    
    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }
} 