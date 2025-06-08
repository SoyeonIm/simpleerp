/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import java.util.List;
import model.Product;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yiming
 */
public class ProductDAOTest {
    
    public ProductDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insert method, of class ProductDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        Product p = new Product("T999", "test product", 10, 99.99);
        ProductDAO instance = new ProductDAO();
        instance.insert(p);
        
        //check if inserted
        List<Product> products = instance.getAll();
        boolean found = false;
        for(Product product : products) {
            if(product.getId().equals("T999")) {
                found = true;
                assertEquals("test product", product.getName());
                break;
            }
        }
        assertTrue("product should be inserted", found);
        
        //cleanup
        instance.delete("T999");
    }

    /**
     * Test of updateQuantity method, of class ProductDAO.
     */
    @Test
    public void testUpdateQuantity() {
        System.out.println("updateQuantity");
        //first insert a test product
        Product p = new Product("T888", "update test", 5, 50.0);
        ProductDAO instance = new ProductDAO();
        instance.insert(p);
        
        //update quantity
        instance.updateQuantity("T888", 15);
        
        //verify update
        List<Product> products = instance.getAll();
        for(Product product : products) {
            if(product.getId().equals("T888")) {
                assertEquals(15, product.getQuantity());
                break;
            }
        }
        
        //cleanup
        instance.delete("T888");
    }

    /**
     * Test of delete method, of class ProductDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        //insert test product first
        Product p = new Product("T777", "delete test", 3, 30.0);
        ProductDAO instance = new ProductDAO();
        instance.insert(p);
        
        //delete it
        instance.delete("T777");
        
        //verify deletion
        List<Product> products = instance.getAll();
        boolean found = false;
        for(Product product : products) {
            if(product.getId().equals("T777")) {
                found = true;
                break;
            }
        }
        assertFalse("product should be deleted", found);
    }

    /**
     * Test of getAll method, of class ProductDAO.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        ProductDAO instance = new ProductDAO();
        List<Product> result = instance.getAll();
        assertNotNull("result should not be null", result);
        //just check that we get a list, dont care about size
        assertTrue("should return a list", result instanceof List);
    }
    
}
