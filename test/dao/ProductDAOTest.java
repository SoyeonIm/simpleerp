package dao;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import product.Product;
import java.util.List;

//test for product dao
public class ProductDAOTest {
    
    private ProductDAO dao;
    
    @Before
    public void setup() {
        dao = new ProductDAO();
    }
    
    @Test
    public void testInsertProduct() {
        Product p = new Product("TEST01", "Test Product", 10, 99.99);
        dao.insert(p);
        
        List<Product> products = dao.getAll();
        boolean found = false;
        for (Product product : products) {
            if (product.getId().equals("TEST01")) {
                found = true;
                assertEquals("Test Product", product.getName());
                assertEquals(10, product.getQuantity());
                assertEquals(99.99, product.getPrice(), 0.01);
                break;
            }
        }
        assertTrue("product should be found after insert", found);
    }
    
    @Test
    public void testGetAllProducts() {
        List<Product> products = dao.getAll();
        assertNotNull("product list should not be null", products);
    }
    
    @Test
    public void testUpdateQuantity() {
        //first add a product
        Product p = new Product("TEST02", "Test Product 2", 5, 49.99);
        dao.insert(p);
        
        //then update its quantity
        dao.updateQuantity("TEST02", 15);
        
        //check if updated
        List<Product> products = dao.getAll();
        for (Product product : products) {
            if (product.getId().equals("TEST02")) {
                assertEquals(15, product.getQuantity());
                break;
            }
        }
    }
    
    @Test
    public void testDeleteProduct() {
        //add a product first
        Product p = new Product("TEST03", "Test Product 3", 3, 29.99);
        dao.insert(p);
        
        //delete it
        dao.delete("TEST03");
        
        //check if deleted
        List<Product> products = dao.getAll();
        for (Product product : products) {
            assertNotEquals("deleted product should not exist", "TEST03", product.getId());
        }
    }
} 