package utils;

import org.junit.Test;

/**
 * basic junit test for utility functions
 * @author soyeon, yiming
 */
public class UtilsTest {
    
    @Test
    public void testStringValidation() {
        System.out.println("testing string validation...");
        
        //test null string
        String nullStr = null;
        boolean isNullEmpty = (nullStr == null || nullStr.trim().isEmpty());
        if (!isNullEmpty) {
            throw new RuntimeException("null string validation failed");
        }
        
        //test empty string
        String emptyStr = "";
        boolean isEmpty = (emptyStr == null || emptyStr.trim().isEmpty());
        if (!isEmpty) {
            throw new RuntimeException("empty string validation failed");
        }
        
        //test whitespace string
        String whitespaceStr = "   ";
        boolean isWhitespace = (whitespaceStr == null || whitespaceStr.trim().isEmpty());
        if (!isWhitespace) {
            throw new RuntimeException("whitespace string validation failed");
        }
        
        //test valid string
        String validStr = "hello world";
        boolean isValid = !(validStr == null || validStr.trim().isEmpty());
        if (!isValid) {
            throw new RuntimeException("valid string validation failed");
        }
        
        System.out.println("string validation test passed");
    }

    @Test
    public void testNumberValidation() {
        System.out.println("testing number validation...");
        
        //test positive numbers
        double positive = 123.45;
        if (positive <= 0) {
            throw new RuntimeException("positive number validation failed");
        }
        
        //test negative numbers
        double negative = -67.89;
        if (negative >= 0) {
            throw new RuntimeException("negative number validation failed");
        }
        
        //test zero
        double zero = 0.0;
        if (zero != 0.0) {
            throw new RuntimeException("zero validation failed");
        }
        
        //test integer conversion
        int intValue = (int) positive;
        if (intValue != 123) {
            throw new RuntimeException("integer conversion failed");
        }
        
        System.out.println("number validation test passed");
    }

    @Test
    public void testStringFormatting() {
        System.out.println("testing string formatting...");
        
        //test basic formatting
        String name = "john";
        String formatted = String.format("Hello, %s!", name);
        if (!"Hello, john!".equals(formatted)) {
            throw new RuntimeException("basic string formatting failed");
        }
        
        //test number formatting
        double price = 123.456;
        String priceFormatted = String.format("%.2f", price);
        if (!"123.46".equals(priceFormatted)) {
            throw new RuntimeException("number formatting failed");
        }
        
        //test multiple parameters
        String multi = String.format("ID: %d, Name: %s, Price: %.2f", 1001, "laptop", 999.99);
        if (!multi.contains("1001") || !multi.contains("laptop") || !multi.contains("999.99")) {
            throw new RuntimeException("multiple parameter formatting failed");
        }
        
        System.out.println("string formatting test passed");
    }

    @Test
    public void testArrayOperations() {
        System.out.println("testing array operations...");
        
        //test array creation
        int[] numbers = {1, 2, 3, 4, 5};
        if (numbers.length != 5) {
            throw new RuntimeException("array creation failed");
        }
        
        //test array access
        if (numbers[0] != 1 || numbers[4] != 5) {
            throw new RuntimeException("array access failed");
        }
        
        //test array sum
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        if (sum != 15) {
            throw new RuntimeException("array sum calculation failed");
        }
        
        //test string array
        String[] names = {"alice", "bob", "charlie"};
        if (names.length != 3 || !"bob".equals(names[1])) {
            throw new RuntimeException("string array operations failed");
        }
        
        System.out.println("array operations test passed");
    }

    @Test
    public void testDateTimeOperations() {
        System.out.println("testing date time operations...");
        
        //test current time
        long currentTime = System.currentTimeMillis();
        if (currentTime <= 0) {
            throw new RuntimeException("current time retrieval failed");
        }
        
        //test time difference
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(10); //sleep for 10ms
        } catch (InterruptedException e) {
            //ignore
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        if (duration < 0) {
            throw new RuntimeException("time difference calculation failed");
        }
        
        System.out.println("date time operations test passed - duration: " + duration + "ms");
    }

    @Test
    public void testMathOperations() {
        System.out.println("testing math operations...");
        
        //test basic arithmetic
        double a = 10.5;
        double b = 3.2;
        
        double sum = a + b;
        if (Math.abs(sum - 13.7) > 0.001) {
            throw new RuntimeException("addition failed");
        }
        
        double diff = a - b;
        if (Math.abs(diff - 7.3) > 0.001) {
            throw new RuntimeException("subtraction failed");
        }
        
        double product = a * b;
        if (Math.abs(product - 33.6) > 0.001) {
            throw new RuntimeException("multiplication failed");
        }
        
        double quotient = a / b;
        if (Math.abs(quotient - 3.28125) > 0.001) {
            throw new RuntimeException("division failed");
        }
        
        //test rounding
        double rounded = Math.round(10.7);
        if (rounded != 11.0) {
            throw new RuntimeException("rounding failed");
        }
        
        System.out.println("math operations test passed");
    }

    @Test
    public void testExceptionHandling() {
        System.out.println("testing exception handling...");
        
        //test division by zero
        try {
            int result = 10 / 0;
            throw new RuntimeException("division by zero should throw exception");
        } catch (ArithmeticException e) {
            //expected exception
        }
        
        //test null pointer
        try {
            String nullStr = null;
            int length = nullStr.length();
            throw new RuntimeException("null pointer should throw exception");
        } catch (NullPointerException e) {
            //expected exception
        }
        
        //test array index out of bounds
        try {
            int[] arr = {1, 2, 3};
            int value = arr[5];
            throw new RuntimeException("array index out of bounds should throw exception");
        } catch (ArrayIndexOutOfBoundsException e) {
            //expected exception
        }
        
        System.out.println("exception handling test passed");
    }

    @Test
    public void testPerformanceMeasurement() {
        System.out.println("testing performance measurement...");
        
        long startTime = System.nanoTime();
        
        //perform some operations
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            sum += i;
        }
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        if (duration <= 0) {
            throw new RuntimeException("performance measurement failed");
        }
        
        if (sum != 499500) {
            throw new RuntimeException("loop calculation failed");
        }
        
        System.out.println("performance measurement test passed - took " + duration + " nanoseconds");
    }
} 