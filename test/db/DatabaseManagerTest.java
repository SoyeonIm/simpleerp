/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package db;

import java.sql.Connection;
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
public class DatabaseManagerTest {
    
    public DatabaseManagerTest() {
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
     * Test of getInstance method, of class DatabaseManager.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        DatabaseManager result1 = DatabaseManager.getInstance();
        DatabaseManager result2 = DatabaseManager.getInstance();
        
        assertNotNull("instance should not be null", result1);
        assertSame("should return same instance (singleton)", result1, result2);
    }

    /**
     * Test of getConnection method, of class DatabaseManager.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        DatabaseManager instance = DatabaseManager.getInstance();
        Connection result = instance.getConnection();
        
        assertNotNull("connection should not be null", result);
        
        try {
            assertFalse("connection should be open", result.isClosed());
        } catch (Exception e) {
            fail("error checking connection: " + e.getMessage());
        }
    }
    
}
