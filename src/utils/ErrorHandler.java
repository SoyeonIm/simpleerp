package utils;

import java.awt.Component;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ErrorHandler {
    
    public static void handleDatabaseError(Exception e) {
        System.out.println("Database error: " + e.getMessage());
        if (e instanceof SQLException) {
            SQLException sqlEx = (SQLException) e;
            System.out.println("SQL State: " + sqlEx.getSQLState());
            System.out.println("Error Code: " + sqlEx.getErrorCode());
        }
    }
    
    public static void handleDatabaseError(Exception e, String operation) {
        System.out.println("Database error during " + operation + ": " + e.getMessage());
        if (e instanceof SQLException) {
            SQLException sqlEx = (SQLException) e;
            System.out.println("SQL State: " + sqlEx.getSQLState());
        }
    }
    
    public static void showErrorDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showErrorDialog(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showWarningDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void showInfoDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean isValidInput(String input) {
        return input != null && !input.trim().isEmpty();
    }
    
    public static boolean isValidNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static void handleGeneralError(Exception e) {
        System.out.println("Error occurred: " + e.getMessage());
        e.printStackTrace();
    }
    
    public static void handleGeneralError(Exception e, String context) {
        System.out.println("Error in " + context + ": " + e.getMessage());
        e.printStackTrace();
    }
} 