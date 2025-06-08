package gui;

import dao.UserDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import utils.ResourceManager;

//simple login window
public class LoginFrame extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserDAO userDAO;
    
    public LoginFrame() {
        userDAO = new UserDAO();
        setupUI();
    }
    
    private void setupUI() {
        setTitle("SimpleERP Login");
        setSize(450, 600);
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
        
        //login card panel
        JPanel loginCard = createLoginCard();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginCard, gbc);
        
        add(mainPanel);
    }
    
    private JPanel createLoginCard() {
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
        card.setPreferredSize(new Dimension(350, 480));
        card.setOpaque(false);
        
        //title
        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(ResourceManager.Colors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Sign in to your account");
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
        
        //buttons
        JButton loginButton = createStyledButton("Sign In", ResourceManager.Colors.PRIMARY);
        loginButton.setIcon(ResourceManager.getResizedIcon("login.png", 16, 16));
        
        JButton registerButton = createStyledButton("Create Account", ResourceManager.Colors.ACCENT);
        
        //add components to form
        formPanel.add(userLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(usernameField);
        formPanel.add(passLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        formPanel.add(loginButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(registerButton);
        
        //add to card
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(subtitleLabel);
        card.add(formPanel);
        
        //add action listeners
        loginButton.addActionListener(this::loginAction);
        registerButton.addActionListener(this::registerAction);
        passwordField.addActionListener(this::loginAction);
        
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
    
    private void loginAction(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please enter both username and password", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        if (userDAO.login(username, password)) {
            showMessage("Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            SwingUtilities.invokeLater(() -> new MainFrame(username).setVisible(true));
        } else {
            showMessage("Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
    
    private void registerAction(ActionEvent e) {
        dispose();
        SwingUtilities.invokeLater(() -> new RegistrationFrame().setVisible(true));
    }
    
    private void showMessage(String message, String title, int type) {
        switch (type) {
            case JOptionPane.ERROR_MESSAGE:
                CustomMessageDialog.showError(this, message, title);
                break;
            case JOptionPane.WARNING_MESSAGE:
                CustomMessageDialog.showWarning(this, message, title);
                break;
            case JOptionPane.INFORMATION_MESSAGE:
                CustomMessageDialog.showInfo(this, message, title);
                break;
            default:
                CustomMessageDialog.showInfo(this, message, title);
                break;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
} 