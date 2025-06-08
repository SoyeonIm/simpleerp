/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

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
public class UserDAOTest {
    
    public UserDAOTest() {
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
     * Test of login method, of class UserDAO.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        UserDAO instance = new UserDAO();
        
        //test valid login
        boolean result1 = instance.login("admin", "admin123");
        assertTrue("admin should login successfully", result1);
        
        //test invalid login
        boolean result2 = instance.login("wrong", "wrong");
        assertFalse("wrong credentials should fail", result2);
        
        //test empty credentials
        boolean result3 = instance.login("", "");
        assertFalse("empty credentials should fail", result3);
    }

    /**
     * Test of register method, of class UserDAO.
     */
    @Test
    public void testRegister() {
        System.out.println("register");
        UserDAO instance = new UserDAO();
        
        //create unique username with random number
        long randomNum = System.currentTimeMillis() + (long)(Math.random() * 1000);
        String testUser = "testuser" + randomNum;
        
        //make sure user doesn't exist first
        while(instance.login(testUser, "testpass")) {
            randomNum++;
            testUser = "testuser" + randomNum;
        }
        
        //test register new user
        boolean result1 = instance.register(testUser, "testpass");
        assertTrue("new user should register successfully", result1);
        
        //test login with new user
        boolean result2 = instance.login(testUser, "testpass");
        assertTrue("new user should be able to login", result2);
        
        //test register existing user (try to register same user again)
        boolean result3 = instance.register(testUser, "testpass");
        assertFalse("same user should not register twice", result3);
    }
    
}
