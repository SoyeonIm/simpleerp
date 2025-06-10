package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import utils.ErrorHandler;

//singleton pattern for database manager
public class DatabaseManager {
    
    private static DatabaseManager instance;
    // Use relative path to the project directory, ensure it can be found on different computers
    private static final String DB_URL = getProjectDatabaseUrl();
    private Connection connection;

    // Get the database URL from the project root directory
    private static String getProjectDatabaseUrl() {
        try {
            // Get the current class file location, and find the project root directory
            String projectRoot = System.getProperty("user.dir");
            String dbPath = projectRoot + File.separator + "simpleerpdb";
            return "jdbc:derby:" + dbPath + ";create=true";
        } catch (Exception e) {
            // If failed, use default relative path
            return "jdbc:derby:simpleerpdb;create=true";
        }
    }

    //private constructor so no one can make new objects
    private DatabaseManager() {
        initializeDatabase();
    }
    
    private void initializeDatabase() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            System.out.println("Attempting to connect to database: " + DB_URL);
            connection = DriverManager.getConnection(DB_URL);
            
            // Ensure auto-commit is enabled for easier transaction handling
            connection.setAutoCommit(true);
            
            System.out.println("database connected successfully with auto-commit: " + connection.getAutoCommit());
            setupTables();
        } catch (Exception e) {
            System.err.println("Failed to connect to main database: " + e.getMessage());
            ErrorHandler.handleDatabaseError(e, "initial connection");
            tryBackupDatabase();
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
                    System.out.println("re-enabled auto-commit on existing connection");
                }
            }
        } catch (SQLException e) {
            System.err.println("Connection check failed: " + e.getMessage());
            ErrorHandler.handleDatabaseError(e, "connection check");
            initializeDatabase();
        }
        
        //Make sure not to return a null connection
        if (connection == null) {
            throw new RuntimeException("Failed to establish database connection. Please check if the database files exist and are accessible.");
        }
        
        return connection;
    }
    
    private void tryBackupDatabase() {
        try {
            String projectRoot = System.getProperty("user.dir");
            String backupDbPath = projectRoot + File.separator + "simpleerpdb_backup";
            String backupUrl = "jdbc:derby:" + backupDbPath + ";create=true";
            System.out.println("Attempting backup database connection: " + backupUrl);
            connection = DriverManager.getConnection(backupUrl);
            connection.setAutoCommit(true);
            System.out.println("connected to backup database successfully");
            setupTables();
        } catch (Exception ex) {
            System.err.println("Backup database connection also failed: " + ex.getMessage());
            ErrorHandler.handleDatabaseError(ex, "backup connection");
        }
    }

    //setup tables when first time run
    private void setupTables() {
        try {
            Statement stmt = connection.createStatement();

            //create employees table
        try {
                stmt.executeUpdate("CREATE TABLE employees (id VARCHAR(10) PRIMARY KEY, name VARCHAR(100), department VARCHAR(100))");
                System.out.println("employees table created");
            } catch (SQLException e) {
                //table already exists, thats fine
            }

            //create products table  
            try {
                stmt.executeUpdate("CREATE TABLE products (id VARCHAR(10) PRIMARY KEY, name VARCHAR(100), quantity INT, price DOUBLE)");
                System.out.println("products table created");
        } catch (SQLException e) {
                //table already exists, thats fine
        }

            //create users table for login
        try {
                stmt.executeUpdate("CREATE TABLE users (username VARCHAR(50) PRIMARY KEY, password VARCHAR(100))");
                System.out.println("users table created");
                
                //add default admin user
                try {
                    stmt.executeUpdate("INSERT INTO users (username, password) VALUES ('admin', 'admin123')");
                    System.out.println("default admin user created");
                } catch (SQLException ex) {
                    //user already exists, thats fine
                }
                
            } catch (SQLException e) {
                //table already exists, thats fine
            }

            stmt.close();
        } catch (SQLException e) {
            ErrorHandler.handleDatabaseError(e, "table setup");
        }
    }
    
    //close database connection properly
    public void closeDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                //ensure all transactions are committed before closing
                if (!connection.getAutoCommit()) {
                    connection.commit();
                    System.out.println("final commit before closing");
                }
                connection.close();
                System.out.println("database connection closed properly");
            }
            
            //shutdown derby database
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException e) {
                //shutdown always throws exception, thats normal for derby
                if (e.getSQLState().equals("XJ015")) {
                    System.out.println("database shutdown successfully");
                } else {
                    System.out.println("database shutdown state: " + e.getSQLState());
                }
            }
        } catch (SQLException e) {
            ErrorHandler.handleDatabaseError(e, "database shutdown");
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

