/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employee;

import interfaces.UserInterface;
import utils.InputHelper;
import java.util.ArrayList;

/**
 * Employee manager class responsible for CRUD operations on employee data
 */
public class EmployeeManager implements UserInterface {
    private ArrayList<Employee> employees = new ArrayList<>();

    //Add a new employee
     
    public void addEmployee(Employee e) {
        employees.add(e);
        System.out.println("Great! New team member added to the system.");
    }

    // remove an employee by ID
    public boolean removeEmployee(String id) {
        for(int i = 0; i < employees.size(); i++) {
            if(employees.get(i).id.equals(id)) {
                employees.remove(i);
                System.out.println("Employee record " + id + " has been deleted from database.");
                return true;
            }
        }
        
        // No employee found
        System.out.println("Hmm, couldn't find anyone with ID " + id + " in our records.");
        return false;
    }

    //Get all employees
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    @Override
    public void displayMenu() {
        boolean stayInMenu = true;
        
        while (stayInMenu) {
            System.out.println("\n=== Staff Management Portal ===");
            System.out.println("1) Register New Employee");
            System.out.println("2) Remove Employee Record");
            System.out.println("3) View All Staff");
            System.out.println("4) Return to Main Menu");
            
            // get
            String choice = InputHelper.getInput("What would you like to do? ");

            // Handle
            switch (choice) {
                case "1":
                    String id = InputHelper.getNonEmptyInput("Staff ID: ");
                    String name = InputHelper.getNonEmptyInput("Full Name: ");
                    String dept = InputHelper.getNonEmptyInput("Department: ");
                    addEmployee(new Employee(id, name, dept));
                    break;
                    
                case "2":
                    String empId = InputHelper.getNonEmptyInput("Enter ID of employee to remove: ");
                    removeEmployee(empId);
                    break;
                    
                case "3":
                    displayEmployees();
                    break;
                    
                case "4":
                    stayInMenu = false;
                    break;
                    
                default:
                    System.out.println("Sorry, that's not a valid option. Try again.");
            }
        }
    }

    // Display all employee
    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No staff records found in the database.");
            return;
        }
        
        System.out.println("-------- CURRENT STAFF --------");
        for (Employee e : employees) {
            e.displayInfo();
        }
        System.out.println("------------------------------");
    }
}

