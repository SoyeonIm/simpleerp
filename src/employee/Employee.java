/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employee;

import person.Person;

public class Employee extends Person {
    private String department;
    private String position;
    private double salary;
    private java.util.Date hireDate;

    public Employee(String id, String name, String department) {
        super(id, name);
        this.department = department;
        this.hireDate = new java.util.Date();
    }
    
    // Extended constructor
    public Employee(String id, String name, String email, String phone, 
                   String address, String department, String position, double salary) {
        super(id, name, email, phone, address);
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.hireDate = new java.util.Date();
    }
    
    // Getters and setters
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public java.util.Date getHireDate() {
        return hireDate;
    }

    @Override
    public void displayInfo() {
        System.out.println("Employee ID: " + id + 
                          ", Name: " + name + 
                          ", Department: " + department +
                          (position != null ? ", Position: " + position : "") +
                          (email != null ? ", Email: " + email : ""));
    }

    @Override
    public String toFileString() {
        return id + "," + name + "," + 
              (email != null ? email : "") + "," + 
              (phone != null ? phone : "") + "," + 
              (address != null ? address : "") + "," + 
              department + "," + 
              (position != null ? position : "") + "," + 
              (salary > 0 ? salary : "0.0");
    }
}

