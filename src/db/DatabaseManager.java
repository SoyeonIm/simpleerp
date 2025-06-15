package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//singleton pattern for database manager
public class DatabaseManager {
    
    private static DatabaseManager instance;
    // Use project-relative path to ensure consistency across different computers
    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final String DB_NAME = PROJECT_DIR + File.separator + "simpleerpdb";
    private static final String BACKUP_DB_NAME = PROJECT_DIR + File.separator + "simpleerpdb_backup";
    private Connection connection;

    //private constructor so no one can make new objects
    private DatabaseManager() {
        initializeDatabase();
    }
    
    private void initializeDatabase() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            
            //clean up any corrupted database files first
            cleanupCorruptedDatabases();
            
            //use simple relative path
            String dbUrl = "jdbc:derby:" + DB_NAME + ";create=true";
            System.out.println("Attempting to connect to database: " + dbUrl);
            connection = DriverManager.getConnection(dbUrl);
            
            // Ensure auto-commit is enabled for easier transaction handling
            connection.setAutoCommit(true);
            
            System.out.println("Database connected successfully!");
            setupTables();
            
        } catch (Exception e) {
            System.err.println("Failed to connect to main database: " + e.getMessage());
            e.printStackTrace();
            tryBackupDatabase();
        }
    }

    // Clean up corrupted database files
    private void cleanupCorruptedDatabases() {
        cleanupDatabase(DB_NAME);
        cleanupDatabase(BACKUP_DB_NAME);
    }
    
    private void cleanupDatabase(String dbName) {
        try {
            File dbDir = new File(dbName);
            if (dbDir.exists() && dbDir.isDirectory()) {
                // Check if service.properties exists
                File serviceProps = new File(dbDir, "service.properties");
                if (!serviceProps.exists()) {
                    System.out.println("Found corrupted database: " + dbName + ", cleaning up...");
                    deleteDirectory(dbDir);
                    System.out.println("Corrupted database cleaned up: " + dbName);
                }
            }
        } catch (Exception e) {
            System.out.println("Warning during database cleanup: " + e.getMessage());
        }
    }
    
    // Helper method to delete directory recursively
    private void deleteDirectory(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            dir.delete();
        }
    }

    //get the only instance
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    //get connection for dao classes
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Connection is null or closed, reinitializing...");
                initializeDatabase();
            } else {
                // Double-check auto-commit is enabled
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                    System.out.println("Re-enabled auto-commit on existing connection");
                }
            }
        } catch (SQLException e) {
            System.err.println("Connection check failed: " + e.getMessage());
            initializeDatabase();
        }
        
        // Make sure not to return a null connection
        if (connection == null) {
            throw new RuntimeException("Failed to establish database connection after all attempts!");
        }
        
        return connection;
    }
    
    private void tryBackupDatabase() {
        try {
            String backupUrl = "jdbc:derby:" + BACKUP_DB_NAME + ";create=true";
            System.out.println("Attempting backup database connection: " + backupUrl);
            connection = DriverManager.getConnection(backupUrl);
            connection.setAutoCommit(true);
            System.out.println("Connected to backup database successfully!");
            setupTables();
        } catch (Exception ex) {
            System.err.println("Backup database connection also failed: " + ex.getMessage());
            ex.printStackTrace();
            // If backup also fails, we have serious issues
            throw new RuntimeException("Both main and backup database connections failed!");
        }
    }

    //setup tables when first time run
    private void setupTables() {
        try {
            Statement stmt = connection.createStatement();

            // Create employees table
            try {
                stmt.executeUpdate("CREATE TABLE employees (id VARCHAR(10) PRIMARY KEY, name VARCHAR(100), department VARCHAR(100))");
                System.out.println("Employees table created");
            } catch (SQLException e) {
                //table already exists, that's fine
                if (!e.getSQLState().equals("X0Y32")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }

            //create products table  
            try {
                stmt.executeUpdate("CREATE TABLE products (id VARCHAR(10) PRIMARY KEY, name VARCHAR(100), quantity INT, price DOUBLE)");
                System.out.println("Products table created");
            } catch (SQLException e) {
                //table already exists, that's fine
                if (!e.getSQLState().equals("X0Y32")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }

            //create users table for login
            try {
                stmt.executeUpdate("CREATE TABLE users (username VARCHAR(50) PRIMARY KEY, password VARCHAR(100))");
                System.out.println("Users table created");
                
                //asd default admin user
                try {
                    stmt.executeUpdate("INSERT INTO users (username, password) VALUES ('admin', 'admin123')");
                    System.out.println("Default admin user created");
                } catch (SQLException ex) {
                    //user already exists, that's fine
                    if (!ex.getSQLState().equals("23505")) {
                        System.out.println("Note: " + ex.getMessage());
                    }
                }
                
            } catch (SQLException e) {
                //table already exists, that's fine
                if (!e.getSQLState().equals("X0Y32")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }

            stmt.close();
            System.out.println("Database tables setup completed!");
            
            // Add sample data if tables are empty (for demonstration purposes)
            insertSampleDataIfEmpty();
            
        } catch (SQLException e) {
            System.err.println("Error setting up tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Insert sample data if tables are empty
    private void insertSampleDataIfEmpty() {
        try {
            // Check if employees table is empty and add sample data
            Statement checkStmt = connection.createStatement();
            ResultSet rs = checkStmt.executeQuery("SELECT COUNT(*) FROM employees");
            if (rs.next() && rs.getInt(1) == 0) {
                // Insert sample employees
                PreparedStatement empStmt = connection.prepareStatement(
                    "INSERT INTO employees (id, name, department) VALUES (?, ?, ?)");
                
                empStmt.setString(1, "E001");
                empStmt.setString(2, "John Smith");
                empStmt.setString(3, "Engineering");
                empStmt.executeUpdate();
                
                empStmt.setString(1, "E002");
                empStmt.setString(2, "Jane Doe");
                empStmt.setString(3, "Marketing");
                empStmt.executeUpdate();
                
                empStmt.setString(1, "E003");
                empStmt.setString(2, "Bob Wilson");
                empStmt.setString(3, "Finance");
                empStmt.executeUpdate();
                
                empStmt.close();
                System.out.println("Sample employees inserted");
            }
            
            // Check if products table is empty and add sample data
            rs = checkStmt.executeQuery("SELECT COUNT(*) FROM products");
            if (rs.next() && rs.getInt(1) == 0) {
                // Insert sample products
                PreparedStatement prodStmt = connection.prepareStatement(
                    "INSERT INTO products (id, name, quantity, price) VALUES (?, ?, ?, ?)");
                
                prodStmt.setString(1, "P001");
                prodStmt.setString(2, "Laptop Computer");
                prodStmt.setInt(3, 25);
                prodStmt.setDouble(4, 1299.99);
                prodStmt.executeUpdate();
                
                prodStmt.setString(1, "P002");
                prodStmt.setString(2, "Office Chair");
                prodStmt.setInt(3, 50);
                prodStmt.setDouble(4, 199.99);
                prodStmt.executeUpdate();
                
                prodStmt.setString(1, "P003");
                prodStmt.setString(2, "Wireless Mouse");
                prodStmt.setInt(3, 100);
                prodStmt.setDouble(4, 29.99);
                prodStmt.executeUpdate();
                
                prodStmt.close();
                System.out.println("Sample products inserted");
            }
            
            rs.close();
            checkStmt.close();
            
        } catch (SQLException e) {
            System.out.println("Note: Could not insert sample data: " + e.getMessage());
        }
    }
    
    //close database connection properly
    public void closeDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                //ensure all transactions are committed before closing
                if (!connection.getAutoCommit()) {
                    connection.commit();
                    System.out.println("Final commit before closing");
                }
                connection.close();
                System.out.println("Database connection closed properly");
            }
            
            //shutdown derby database
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException e) {
                //shutdown always throws exception, thats normal for derby
                if (e.getSQLState().equals("XJ015")) {
                    System.out.println("Database shutdown successfully");
                } else {
                    System.out.println("Database shutdown state: " + e.getSQLState());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database shutdown: " + e.getMessage());
        }
    }
    
    //shutdown hook to ensure database closes properly
    public static void setupShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (instance != null) {
                instance.closeDatabase();
            }
        }));
    }
}

