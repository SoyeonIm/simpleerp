/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final String LOG_FILE = "system.log";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static boolean consoleOutput = true; 
    
    public static void setConsoleOutput(boolean enabled) {
        consoleOutput = enabled;
    }
    
    public static boolean isConsoleOutputEnabled() {
        return consoleOutput;
    }
    
    public static void info(String message) {
        log("INFO", message);
    }
    
    public static void error(String message) {
        log("ERROR", message);
    }
    
    public static void warning(String message) {
        log("WARNING", message);
    }
    
    private static void log(String level, String message) {
        String logEntry = DATE_FORMAT.format(new Date()) + " [" + level + "] " + message;
        
        if (consoleOutput) {
            System.out.println(logEntry);
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            System.err.println("Cannot write to log file: " + e.getMessage());
        }
    }
}
