/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package product;

import interfaces.UserInterface;
import utils.InputHelper;
import java.util.HashMap;

public class InventoryManager implements UserInterface {
    // use hashmap to store products, key is product id, value is product instanc
    private HashMap<String, Product> products = new HashMap<>();

    
    public void addProduct(Product p) {
        // check
        if(products.containsKey(p.getId())) {
            System.out.println("Notice: Updating existing product information");
        }
        
        // add, update
        products.put(p.getId(), p);
        System.out.println("Product " + p.getName() + " has been registered in inventory");
    }

    public void removeProduct(String id) {
        if(products.containsKey(id)) {
            Product removed = products.remove(id);
            System.out.println("Product '" + removed.getName() + "' has been removed from inventory");
        } else {
            System.out.println("Sorry, couldn't find product with ID: " + id);
        }
    }

    public void updateProductQuantity(String id, int quantity) {
        Product item = products.get(id);
        
        if (item == null) {
            System.out.println("Error: Product ID " + id + " not found in database");
            return;
        }
        
        // record old quantity
        int oldQty = item.getQuantity();
        item.setQuantity(quantity);
        
        // display result
        if(quantity > oldQty) {
            System.out.println("Stock increased: " + (quantity - oldQty) + " units added");
        } else if(quantity < oldQty) {
            System.out.println("Stock decreased: " + (oldQty - quantity) + " units removed");
        } else {
            System.out.println("Stock count verified - no change");
        }
    }

    public HashMap<String, Product> getProducts() {
        return products;
    }

    @Override
    public void displayMenu() {
        String userChoice;
        
        do {
            System.out.println("\n===== INVENTORY CONTROL =====");
            System.out.println("1. Register New Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Update Stock Level");
            System.out.println("4. View All Products");
            System.out.println("5. Return to Main Menu");
            
            userChoice = InputHelper.getInput("Select option: ");
            
            if(userChoice.equals("1")) {
                // add product
                String id = InputHelper.getInput("Product ID: ");
                String name = InputHelper.getInput("Product Name: ");
                int quantity = InputHelper.getInt("Initial Stock: ");
                double price = InputHelper.getDouble("Unit Price: $");
                
                Product newProduct = new Product(id, name, quantity, price);
                addProduct(newProduct);
            }
            else if(userChoice.equals("2")) {
                // delete product
                String id = InputHelper.getInput("Enter Product ID to remove: ");
                removeProduct(id);
            }
            else if(userChoice.equals("3")) {
                // update stock level
                String id = InputHelper.getInput("Product ID: ");
                
                // check if product exists
                if(!products.containsKey(id)) {
                    System.out.println("Error: Product not found");
                    continue;
                }
                
                int quantity = InputHelper.getInt("New stock level: ");
                updateProductQuantity(id, quantity);
            }
            else if(userChoice.equals("4")) {
                // all products
                displayProducts();
            }
            else if(userChoice.equals("5")) {
                // exit 
                System.out.println("Returning to main menu...");
                return;
            }
            else {
                System.out.println("Invalid selection! Please try again.");
            }
            
        } while(true);
    }

    public void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product p : products.values()) {
                p.displayProduct();
            }
        }
    }
    
    // ✅ GUI에서 제품 목록을 가져갈 수 있게 공개 메서드 추가
    public java.util.List<Product> getAllProducts() {
        return new java.util.ArrayList<>(products.values());
}

// 제품 하나 가져오기
    public Product getProductById(String id) {
    return products.get(id);
}


    
}

