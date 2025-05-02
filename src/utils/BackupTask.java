/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import interfaces.RunnableTask;
import employee.Employee;
import employee.EmployeeManager;
import product.Product;
import product.InventoryManager;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * data backup task that runs in background
 * 
 * @author soyeon,yiming
 */
public class BackupTask implements RunnableTask {
    private String taskName;
    private String status;
    private boolean running;
    private boolean paused;
    private EmployeeManager employeeManager;
    private InventoryManager inventoryManager;
    
    public BackupTask(String taskName, EmployeeManager empManager, InventoryManager invManager) {
        this.taskName = taskName;
        this.status = "Initialized";
        this.running = false;
        this.paused = false;
        this.employeeManager = empManager;
        this.inventoryManager = invManager;
    }
    
    @Override
    public void run() {
        running = true;
        status = "Running";
        
        try {
            while (running) { 
                if (!paused) {
                    Logger.info("Executing backup task: " + taskName);
                    performBackup();
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    status = "Last backup time: " + sdf.format(new Date());
                }
                
                try {
                    Thread.sleep(5000); // 5 second interval 
                } catch (InterruptedException e) { 
                    Logger.error("Backup task interrupted: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            status = "Error: " + e.getMessage();
            Logger.error("Backup error: " + e.getMessage());
        } finally {
            status = "Stopped";
            running = false;
        }
    }
    
    private void performBackup() {
        try {
            // create backup directory
            File backupDir = new File("backups");
            if (!backupDir.exists()) {
                backupDir.mkdir();
            }
            
            // create timestamped folder
            SimpleDateFormat dirFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dirFormat.format(new Date());
            File currentBackupDir = new File("backups/" + timestamp);
            currentBackupDir.mkdir();
            
            // save data
            saveData(currentBackupDir.getPath() + "/employees_backup.txt", true);
            saveData(currentBackupDir.getPath() + "/products_backup.txt", false);
            
            Logger.info("Backup completed to: " + currentBackupDir.getPath());
        } catch (Exception e) {
            Logger.error("Backup failed: " + e.getMessage());
        }
    }
    
    private void saveData(String filePath, boolean isEmployee) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            if (isEmployee) {
                for (Employee e : employeeManager.getEmployees()) {
                    writer.write(e.toFileString());
                    writer.newLine();
                }
            } else {
                for (Product p : inventoryManager.getProducts().values()) {
                    writer.write(p.toFileString());
                    writer.newLine();
                }
            }
        }
    }
    
    @Override
    public String getTaskName() {
        return taskName; 
    }
    
    @Override
    public String getStatus() {
        return status;
    }
    
    @Override
    public void pauseTask() {
        paused = true;  
        status = "Paused";
        Logger.info("Backup task paused: " + taskName);
    }
    
    @Override
    public void resumeTask() {
        if (paused) {  
            paused = false;
            status = "Running";
            Logger.info("Backup task resumed: " + taskName);
        }
    }
    
    public void stopTask() {
        running = false;  
        Logger.info("Backup task stopped: " + taskName);
    }
}