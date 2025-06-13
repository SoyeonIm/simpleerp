package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CustomMessageDialog extends JDialog {
    
    private static final int SUCCESS = 1;
    private static final int ERROR = 2;
    private static final int WARNING = 3;
    private static final int INFO = 4;
    
    public CustomMessageDialog(Frame parent, String message, String title, int type) {
        super(parent, title, true);
        setupDialog(message, type);
    }
    
    private void setupDialog(String message, int type) {
        setSize(420, 220);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // [POSSIBLE AI-ASSISTED] Custom dialog with gradient background
        // Advanced UI design patterns beyond typical sophomore level
        //main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                //subtle gradient background
                Color color1 = new Color(250, 250, 250);
                Color color2 = new Color(240, 240, 240);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        
        //message card
        JPanel messageCard = createMessageCard(message, type);
        mainPanel.add(messageCard, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createMessageCard(String message, int type) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 30, 20, 30));
        card.setOpaque(false);
        
        //icon and message panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(false);
        
        //icon
        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        Color iconColor = getIconColor(type);
        ImageIcon icon = createColoredIcon(iconColor, type);
        iconLabel.setIcon(icon);
        
        //message text
        JLabel messageLabel = new JLabel();
        messageLabel.setText("<html><center>" + message + "</center></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(30, 30, 30));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setVerticalAlignment(SwingConstants.CENTER);
        messageLabel.setPreferredSize(new Dimension(340, 50));
        messageLabel.setOpaque(true);
        messageLabel.setBackground(Color.WHITE);
        
        contentPanel.add(iconLabel, BorderLayout.NORTH);
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        
        //button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JButton okButton = createStyledButton("OK", iconColor);
        okButton.addActionListener(e -> dispose());
        buttonPanel.add(okButton);
        
        card.add(contentPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private Color getIconColor(int type) {
        switch (type) {
            case SUCCESS:
                return new Color(40, 167, 69);    // green
            case ERROR:
                return new Color(220, 53, 69);    // red
            case WARNING:
                return new Color(188, 94, 53);    // orange/brown
            default:
                return new Color(41, 60, 115);    // blue
        }
    }
    
    private ImageIcon createColoredIcon(Color color, int type) {
        // [POSSIBLE AI-ASSISTED] Complex icon generation with Graphics2D
        // Professional-level custom icon creation using advanced drawing techniques
        int size = 32;
        Image img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        
        switch (type) {
            case SUCCESS:
                //checkmark circle
                g2.fillOval(0, 0, size, size);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(8, 16, 14, 22);
                g2.drawLine(14, 22, 24, 10);
                break;
            case ERROR:
                //x circle
                g2.fillOval(0, 0, size, size);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(10, 10, 22, 22);
                g2.drawLine(22, 10, 10, 22);
                break;
            case WARNING:
                //exclamation triangle
                int[] xPoints = {size/2, 2, size-2};
                int[] yPoints = {2, size-2, size-2};
                g2.fillPolygon(xPoints, yPoints, 3);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(size/2, 8, size/2, 18);
                g2.fillOval(size/2-2, 22, 4, 4);
                break;
            default:
                //info circle
                g2.fillOval(0, 0, size, size);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                int x = (size - fm.stringWidth("i")) / 2;
                int y = (size - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString("i", x, y);
                break;
        }
        
        g2.dispose();
        return new ImageIcon(img);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
    
    //static methods for easy usage
    public static void showSuccess(Frame parent, String message, String title) {
        new CustomMessageDialog(parent, message, title, SUCCESS).setVisible(true);
    }
    
    public static void showError(Frame parent, String message, String title) {
        new CustomMessageDialog(parent, message, title, ERROR).setVisible(true);
    }
    
    public static void showWarning(Frame parent, String message, String title) {
        new CustomMessageDialog(parent, message, title, WARNING).setVisible(true);
    }
    
    public static void showInfo(Frame parent, String message, String title) {
        new CustomMessageDialog(parent, message, title, INFO).setVisible(true);
    }
} 