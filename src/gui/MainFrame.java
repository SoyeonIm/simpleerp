package gui;

import db.DatabaseManager;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import utils.ResourceManager;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;
    private String currentUser;

    public MainFrame(String username) {
        this.currentUser = username;
        
        // Setup database shutdown hook for proper data persistence
        DatabaseManager.setupShutdownHook();
        
        initComponents();
        setupWindow();
        setupCloseOperation();
    }
    
    private void setupWindow() {
        setTitle("Simple ERP System - GUI");
        setMinimumSize(new Dimension(1000, 700));
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void setupCloseOperation() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("closing application and database...");
                DatabaseManager.getInstance().closeDatabase();
                System.exit(0);
            }
        });
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ResourceManager.Colors.DEEP_BLUE);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Simple ERP System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setIcon(ResourceManager.getResizedIcon("product.png", 32, 32));
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(ResourceManager.Colors.DEEP_BLUE);
        
        JLabel userLabel = new JLabel("Welcome, " + currentUser);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        userLabel.setIcon(ResourceManager.getResizedIcon("user.png", 20, 20));
        
        JButton logoutBtn = createHeaderButton("Logout");
        logoutBtn.addActionListener(e -> logout());
        
        userPanel.add(userLabel);
        userPanel.add(Box.createHorizontalStrut(15));
        userPanel.add(logoutBtn);
        
        header.add(titleLabel, BorderLayout.WEST);
        header.add(userPanel, BorderLayout.EAST);
        
        return header;
    }
    
    private JButton createHeaderButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ResourceManager.Colors.ACCENT);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(80, 30));
        
        Color originalColor = button.getBackground();
        Color hoverColor = originalColor.darker();
        
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
    
    private JPanel createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ResourceManager.Colors.BACKGROUND);
        
        tabbedPane = createModernTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private JTabbedPane createModernTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 15));
        tabs.setBackground(ResourceManager.Colors.BACKGROUND);
        tabs.setForeground(ResourceManager.Colors.TEXT_PRIMARY);
        tabs.setTabPlacement(JTabbedPane.TOP);
        
        // Create panels with scroll panes for better responsiveness
        JScrollPane empScroll = new JScrollPane(new EmployeePanel());
        JScrollPane prodScroll = new JScrollPane(new ProductPanel());
        JScrollPane reportScroll = new JScrollPane(new ReportPanel());
        
        empScroll.setBorder(null);
        prodScroll.setBorder(null);
        reportScroll.setBorder(null);
        
        // Create white/contrasted icons for tabs
        ImageIcon empIcon = ResourceManager.getResizedIcon("user.png", 28, 28);  // Use user.png instead
        ImageIcon prodIcon = ResourceManager.getResizedIcon("product.png", 28, 28);
        ImageIcon reportIcon = ResourceManager.getResizedIcon("repoet.png", 28, 28);
        
        tabs.addTab("   Employees   ", empIcon, empScroll);
        tabs.addTab("   Products   ", prodIcon, prodScroll);
        tabs.addTab("   Reports   ", reportIcon, reportScroll);
        
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        customizeTabAppearance(tabs);
        
        return tabs;
    }
    
    private void customizeTabAppearance(JTabbedPane tabs) {
        try {
            UIManager.put("TabbedPane.selected", ResourceManager.Colors.PRIMARY);
            UIManager.put("TabbedPane.background", ResourceManager.Colors.BACKGROUND);
            UIManager.put("TabbedPane.foreground", ResourceManager.Colors.TEXT_PRIMARY);
            UIManager.put("TabbedPane.selectedForeground", Color.WHITE);
            UIManager.put("TabbedPane.tabInsets", new Insets(10, 15, 10, 15));
            UIManager.put("TabbedPane.contentBorderInsets", new Insets(5, 0, 0, 0));
            
            SwingUtilities.updateComponentTreeUI(tabs);
        } catch (Exception e) {
            System.out.println("could not customize tabs: " + e.getMessage());
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            DatabaseManager.getInstance().closeDatabase();
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        }
    }
}
