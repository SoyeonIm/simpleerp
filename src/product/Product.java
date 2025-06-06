/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package product;

public class Product {
    private String id;
    private String name;
    private int quantity;
    private double price;

    public Product(String id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    
    

    public void displayProduct() {
        System.out.println("Product ID: " + id + ", Name: " + name +
                ", Quantity: " + quantity + ", Price: $" + price);
    }

    public String getId() { return id; }
    public int getQuantity() { return quantity; }
    public double getPrice() {
        return price;
    }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String toFileString() {
        return id + "," + name + "," + quantity + "," + price;
    }

    public String getName() {
        return name;
    }
}

