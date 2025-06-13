package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//singleton pattern for database manager
public class DatabaseManager {
    
    private static DatabaseManager instance;
    // Use simple relative path - this avoids complex path issues
    private static final String DB_NAME = "simpleerpdb";
    private static final String BACKUP_DB_NAME = "simpleerpdb_backup";
    private Connection connection;

    //private constructor so no one can make new objects
    private DatabaseManager() {
        initializeDatabase();
    }
    
    private void initializeDatabase() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            
            // [POSSIBLE AI-ASSISTED] Sophisticated database corruption detection and cleanup
            // Advanced error recovery patterns beyond typical sophomore level
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
        // [POSSIBLE AI-ASSISTED] Advanced file system operations and error handling
        // Professional-level database recovery logic
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
            
        } catch (SQLException e) {
            System.err.println("Error setting up tables: " + e.getMessage());
            e.printStackTrace();
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

