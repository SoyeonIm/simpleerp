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
    
    //get resized icon with high quality scaling
    public static ImageIcon getResizedIcon(String iconName, int width, int height) {
        //This part is completed with ai assistance
        ImageIcon icon = getIcon(iconName);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return createDefaultIcon(width, height);
    }
    
    //get background image
    public static ImageIcon getBackgroundImage(String imageName) {
        try {
            URL url = ResourceManager.class.getResource("/resources/images/" + imageName);
            if (url != null) {
                return new ImageIcon(url);
            }
        } catch (Exception e) {
            System.out.println("error loading background: " + imageName);
        }
        return null;
    }
    
    //create default icon when resource not found
    private static ImageIcon createDefaultIcon() {
        return createDefaultIcon(24, 24);
    }
    
    private static ImageIcon createDefaultIcon(int width, int height) {
        
        //create simple colored rectangle as default，This part is completed with ai assistance
        Image img = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();
        g2.setColor(new Color(100, 150, 200));
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.WHITE);
        g2.drawRect(0, 0, width-1, height-1);
        g2.dispose();
        return new ImageIcon(img);
    }
    
    //color scheme based on your selected palette
    public static class Colors {
        // Main palette from color card
        public static final Color DEEP_BLUE = new Color(41, 60, 115);      // #293C73
        public static final Color NAVY_BLUE = new Color(37, 50, 89);       // #253259  
        public static final Color WARM_BROWN = new Color(188, 94, 53);     // #BC5E35
        public static final Color LIGHT_BROWN = new Color(191, 152, 122);  // #BF987A
        public static final Color LIGHT_GRAY = new Color(217, 216, 215);   // #D9D8D7
        
        // UI Colors based on palette - improved contrast
        public static final Color PRIMARY = new Color(65, 90, 140);       // Lighter blue for better contrast
        public static final Color PRIMARY_DARK = NAVY_BLUE;
        public static final Color ACCENT = WARM_BROWN;
        public static final Color ACCENT_LIGHT = LIGHT_BROWN;
        public static final Color BACKGROUND = new Color(248, 249, 250);
        public static final Color CARD_BG = Color.WHITE;
        public static final Color BORDER = new Color(200, 200, 200);
        
        // Text colors - improved readability
        public static final Color TEXT_PRIMARY = new Color(25, 30, 35);
        public static final Color TEXT_SECONDARY = new Color(90, 100, 110);
        public static final Color TEXT_ON_PRIMARY = Color.WHITE;
        public static final Color ICON_CONTRAST = new Color(255, 255, 255);  // White icons for blue backgrounds
        
        // Status colors - better visibility
        public static final Color SUCCESS = new Color(34, 139, 34);        // Forest green
        public static final Color DANGER = new Color(220, 53, 69);
        public static final Color WARNING = new Color(255, 140, 0);        // Dark orange
    }
} 