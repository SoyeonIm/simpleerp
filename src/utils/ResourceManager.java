package utils;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

//resource manager for loading icons and images
public class ResourceManager {
    
    //load icon from resources/icons/
    public static ImageIcon getIcon(String iconName) {
        try {
            URL url = ResourceManager.class.getResource("/resources/icons/" + iconName);
            if (url != null) {
                return new ImageIcon(url);
            } else {
                //fallback to default icon if file not found
                return createDefaultIcon();
            }
        } catch (Exception e) {
            System.out.println("error loading icon: " + iconName);
            return createDefaultIcon();
        }
    }
    
    //load image from resources/images/
    public static ImageIcon getImage(String imageName) {
        try {
            URL url = ResourceManager.class.getResource("/resources/images/" + imageName);
            if (url != null) {
                return new ImageIcon(url);
            }
        } catch (Exception e) {
            System.out.println("error loading image: " + imageName);
        }
        return null;
    }
    
    //get resized icon
    public static ImageIcon getResizedIcon(String iconName, int width, int height) {
        ImageIcon icon = getIcon(iconName);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return createDefaultIcon(width, height);
    }
    
    //create default icon when resource not found
    private static ImageIcon createDefaultIcon() {
        return createDefaultIcon(24, 24);
    }
    
    private static ImageIcon createDefaultIcon(int width, int height) {
        //create simple colored rectangle as default
        Image img = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();
        g2.setColor(new Color(100, 150, 200));
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.WHITE);
        g2.drawRect(0, 0, width-1, height-1);
        g2.dispose();
        return new ImageIcon(img);
    }
    
    //color scheme for modern ui
    public static class Colors {
        public static final Color PRIMARY = new Color(74, 144, 226);
        public static final Color PRIMARY_DARK = new Color(54, 104, 186);  
        public static final Color SECONDARY = new Color(108, 117, 125);
        public static final Color SUCCESS = new Color(40, 167, 69);
        public static final Color DANGER = new Color(220, 53, 69);
        public static final Color WARNING = new Color(255, 193, 7);
        public static final Color BACKGROUND = new Color(248, 249, 250);
        public static final Color CARD_BG = Color.WHITE;
        public static final Color TEXT_PRIMARY = new Color(33, 37, 41);
        public static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    }
} 