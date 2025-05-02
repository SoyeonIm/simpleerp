/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fileio;

import employee.Employee;
import employee.EmployeeManager;
import product.InventoryManager;
import product.Product;
import java.io.*;

public class FileHandler {
    private static final String EMPLOYEE_FILE = "employees.txt";
    private static final String PRODUCT_FILE = "products.txt";
    
    public void loadEmployees(EmployeeManager empManager) {
        if (!fileExists(EMPLOYEE_FILE)) {
            System.out.println("No employee records found - starting with empty database");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE))) {
            String record;
            int recordNum = 0;
            
            while ((record = reader.readLine()) != null) {
                recordNum++;
                
                try {
                    String[] fields = record.split(",");
                    
                    if (fields.length < 3) {
                        System.out.println("Record #" + recordNum + " is missing fields - skipping");
                        continue;
                    }
                    
                    empManager.addEmployee(new Employee(fields[0], fields[1], fields[2]));
                    
                } catch (Exception ex) {
                    System.out.println("Error in record #" + recordNum + " - " + ex.toString());
                }
            }
            
            System.out.println("Staff database loaded successfully");
            
        } catch (IOException ex) {
            System.out.println("Problem accessing employee database: " + ex.toString());
        }
    }

    public void saveEmployees(EmployeeManager empManager) {
        saveData(EMPLOYEE_FILE, empManager.getEmployees(), null);
    }

    public void loadProducts(InventoryManager invManager) {
        if (!fileExists(PRODUCT_FILE)) {
            System.out.println("No inventory data available");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            int lineNum = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNum++;
                
                if(line.trim().isEmpty()) continue;
                
                try {
                    String[] data = line.split(",");
                    
                    if (data.length < 4) {
                        System.out.println("Line " + lineNum + ": Not enough product information");
                        continue;
                    }
                    
                    int qty = Integer.parseInt(data[2].trim());
                    double price = Double.parseDouble(data[3].trim());
                    
                    Product item = new Product(data[0], data[1], qty, price);
                    invManager.addProduct(item);
                    
                } catch (NumberFormatException e) {
                    System.out.println("Line " + lineNum + ": Invalid number format");
                } catch (Exception e) {
                    System.out.println("Error in line " + lineNum + ": " + e.toString());
                }
            }
            
            System.out.println("Inventory data loaded");
            
        } catch (IOException e) {
            System.out.println("Failed to access inventory data: " + e.getMessage());
        }
    }

    public void saveProducts(InventoryManager invManager) {
        saveData(PRODUCT_FILE, null, invManager.getProducts().values());
    }
    
    private boolean fileExists(String filename) {
        return new File(filename).exists();
    }
    
    private void saveData(String filename, Iterable<Employee> employees, Iterable<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            if (employees != null) {
                for (Employee e : employees) {
                    writer.write(e.toFileString());
                    writer.newLine();
                }
            } else if (products != null) {
                for (Product p : products) {
                    writer.write(p.toFileString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to save data to " + filename);
        }
    }
}