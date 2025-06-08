package db;

import java.io.File;

public class DatabaseReset {
    public static void main(String[] args) {
        System.out.println("🔄 Starting database reset...");
        
        //Delete database files
        deleteDirectory(new File("simpleerpdb"));
        deleteDirectory(new File("simpleerpdb_backup"));
        deleteDirectory(new File("derby.log"));
        
        System.out.println("Old database files deleted");
        System.out.println("Database files removed!");
        System.out.println("Next time you run the application, it will recreate database with:");
        System.out.println("Employees: Walter White, Jesse Pinkman, Saul Goodman, Mike Ehrmantraut");
        System.out.println("Products: Blue Crystal, Lab Equipment, Safety Goggles, Chemical Suit");
        System.out.println("Login: admin/admin123 or test/test123");
    }
    
    private static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        boolean deleted = file.delete();
                        if (deleted) {
                            System.out.println("Deleted: " + file.getName());
                        }
                    }
                }
            }
            boolean deleted = directory.delete();
            if (deleted) {
                System.out.println("Deleted directory: " + directory.getName());
            }
        }
    }
} 