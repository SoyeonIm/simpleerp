/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package person;

public abstract class Person {
    public String id;
    protected String name;
    protected String email;
    protected String phone;
    protected String address;
    protected java.util.Date dateCreated;
    
    public Person(String id, String name) {
        this.id = id;
        this.name = name;
        this.dateCreated = new java.util.Date();
    }
    
    // Extended constructor with additional fields
    public Person(String id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dateCreated = new java.util.Date();
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public java.util.Date getDateCreated() {
        return dateCreated;
    }
    
    // Abstract methods
    public abstract void displayInfo();
    public abstract String toFileString();
}

