/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package report;

import employee.EmployeeManager;
import product.InventoryManager;

public class ReportGenerator {
    public void generate(EmployeeManager empManager, InventoryManager invManager) {
        System.out.println("\n--- System Report ---");
        System.out.println("Total Employees: " + empManager.getEmployees().size());
        System.out.println("Total Products: " + invManager.getProducts().size());
        System.out.println("----------------------");
    }
}

