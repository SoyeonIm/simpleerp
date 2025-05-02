/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleerp;

import employee.EmployeeManager;
import fileio.FileHandler;
import product.InventoryManager;
import report.ReportGenerator;
import utils.InputHelper;
import utils.Logger;

public class Main {
    public static void main(String[] args) {
        // Close the console output of the Logger, but it will still be recorded in the file
        Logger.setConsoleOutput(false);
        
        EmployeeManager employeeManager = new EmployeeManager();
        InventoryManager inventoryManager = new InventoryManager();
        ReportGenerator reportGenerator = new ReportGenerator();
        FileHandler fileHandler = new FileHandler();


        fileHandler.loadEmployees(employeeManager);
        fileHandler.loadProducts(inventoryManager);

        while (true) {
            System.out.println("\n=== SIMPLE ERP SYSTEM ===");
            System.out.println("1. Manage Employees");
            System.out.println("2. Manage Products");
            System.out.println("3. Generate Reports");
            System.out.println("4. Save & Exit");
            String option = InputHelper.getInput("Select an option: ");

            switch (option) {
                case "1" -> employeeManager.displayMenu();
                case "2" -> inventoryManager.displayMenu();
                case "3" -> reportGenerator.generate(employeeManager, inventoryManager);
                case "4" -> {
                    fileHandler.saveEmployees(employeeManager);
                    fileHandler.saveProducts(inventoryManager);
                    System.out.println("Data saved. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please select again.");
            }
        }
    }
}

