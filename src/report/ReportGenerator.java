package report;

import employee.EmployeeManager;
import product.InventoryManager;

public class ReportGenerator {
    private EmployeeManager employeeManager;
    private InventoryManager inventoryManager;

    public ReportGenerator(EmployeeManager em, InventoryManager im) {
        this.employeeManager = em;
        this.inventoryManager = im;
    }

    public int getTotalEmployees() {
        return employeeManager.getAllEmployees().size();
    }

    public int getTotalProducts() {
        return inventoryManager.getAllProducts().size();
    }
}
