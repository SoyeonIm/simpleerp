package utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class InputValidatorTest {
    
    @Test
    public void testIsEmpty() {
        assertTrue(InputValidator.isEmpty(null));
        assertTrue(InputValidator.isEmpty(""));
        assertTrue(InputValidator.isEmpty("   "));
        assertFalse(InputValidator.isEmpty("test"));
        assertFalse(InputValidator.isEmpty(" test "));
    }
    
    @Test
    public void testIsValidNumber() {
        assertTrue(InputValidator.isValidNumber("123"));
        assertTrue(InputValidator.isValidNumber("0"));
        assertTrue(InputValidator.isValidNumber("-5"));
        assertFalse(InputValidator.isValidNumber("abc"));
        assertFalse(InputValidator.isValidNumber("12.5"));
        assertFalse(InputValidator.isValidNumber(""));
        assertFalse(InputValidator.isValidNumber(null));
    }
    
    @Test
    public void testIsValidDouble() {
        assertTrue(InputValidator.isValidDouble("123.45"));
        assertTrue(InputValidator.isValidDouble("0.0"));
        assertTrue(InputValidator.isValidDouble("123"));
        assertFalse(InputValidator.isValidDouble("abc"));
        assertFalse(InputValidator.isValidDouble(""));
        assertFalse(InputValidator.isValidDouble(null));
    }
    
    @Test
    public void testIsPositiveNumber() {
        assertTrue(InputValidator.isPositiveNumber("123"));
        assertTrue(InputValidator.isPositiveNumber("1"));
        assertFalse(InputValidator.isPositiveNumber("0"));
        assertFalse(InputValidator.isPositiveNumber("-5"));
        assertFalse(InputValidator.isPositiveNumber("abc"));
    }
    
    @Test
    public void testIsPositiveDouble() {
        assertTrue(InputValidator.isPositiveDouble("123.45"));
        assertTrue(InputValidator.isPositiveDouble("0.1"));
        assertFalse(InputValidator.isPositiveDouble("0.0"));
        assertFalse(InputValidator.isPositiveDouble("-5.5"));
        assertFalse(InputValidator.isPositiveDouble("abc"));
    }
} 