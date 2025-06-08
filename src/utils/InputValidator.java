package utils;

public class InputValidator {
    
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
    
    public static boolean isValidNumber(String input) {
        if (isEmpty(input)) {
            return false;
        }
        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidDouble(String input) {
        if (isEmpty(input)) {
            return false;
        }
        try {
            Double.parseDouble(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isPositiveNumber(String input) {
        if (!isValidNumber(input)) {
            return false;
        }
        return Integer.parseInt(input.trim()) > 0;
    }
    
    public static boolean isPositiveDouble(String input) {
        if (!isValidDouble(input)) {
            return false;
        }
        return Double.parseDouble(input.trim()) > 0;
    }
    
    public static String getErrorMessage(String fieldName) {
        return fieldName + " cannot be empty";
    }
    
    public static String getNumberErrorMessage(String fieldName) {
        return fieldName + " must be a valid number";
    }
    
    public static String getPositiveNumberErrorMessage(String fieldName) {
        return fieldName + " must be a positive number";
    }
} 