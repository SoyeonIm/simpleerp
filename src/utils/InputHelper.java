/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Scanner;

public class InputHelper {
    private static Scanner scanner = new Scanner(System.in);

    public static String getInput(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
    
    public static String getNonEmptyInput(String message) {
        String input;
        do {
            input = getInput(message);
            if (input.isEmpty()) {
                System.out.println("input cannot be empty, try again");
            }
        } while (input.isEmpty());
        return input;
    }

    public static int getInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(getInput(message));
            } catch (NumberFormatException e) {
                System.out.println("invalid number, try again");
            }
        }
    }
    
    public static int getIntInRange(String message, int min, int max) {
        int value;
        do {
            value = getInt(message);
            if (value < min || value > max) {
                System.out.println("input must be between " + min + " and " + max);
            }
        } while (value < min || value > max);
        return value;
    }

    public static double getDouble(String message) {
        while (true) {
            try {
                return Double.parseDouble(getInput(message));
            } catch (NumberFormatException e) {
                System.out.println("invalid decimal, try again");
            }
        }
    }
    
    public static double getDoubleInRange(String message, double min, double max) {
        double value;
        do {
            value = getDouble(message);
            if (value < min || value > max) {
                System.out.println("input must be between " + min + " and " + max);
            }
        } while (value < min || value > max);
        return value;
    }
    
    public static boolean getYesNo(String message) {
        while (true) {
            String input = getInput(message + " (y/n): ").toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println("please enter y or n");
        }
    }
    
    public static void closeScanner() {
        scanner.close();
    }
}
