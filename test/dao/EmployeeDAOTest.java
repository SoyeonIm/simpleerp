package dao;

import model.Employee;
import java.util.List;
import org.junit.Test;

/**
 * basic junit test for employee dao
 * @author soyeon, yiming
 */
public class EmployeeDAOTest {
    
    @Test
    public void testInsertEmployee() {
        System.out.println("testing insert single employee...");
        
        EmployeeDAO dao = new EmployeeDAO();
        Employee emp = new Employee(2001, "john smith", "engineering");
        dao.insert(emp);
        
        List<Employee> list = dao.getAll();
        boolean found = false;
        for (Employee e : list) {
            if (e.getId() == 2001) {
                found = true;
                break;
            }
        }
        
        if (!found) {
            throw new RuntimeException("employee not inserted");
        }
        
        System.out.println("insert single employee test passed");
    }

    @Test
    public void testGetAllEmployees() {
        System.out.println("testing get all employees...");
        
        EmployeeDAO dao = new EmployeeDAO();
        List<Employee> result = dao.getAll();
        
        if (result == null) {
            throw new RuntimeException("employee list is null");
        }
        
        System.out.println("get all employees test passed - found " + result.size() + " employees");
    }

    @Test
    public void testDeleteEmployee() {
        System.out.println("testing delete employee...");
        
        EmployeeDAO dao = new EmployeeDAO();
        
        //insert test employee first
        Employee emp = new Employee(2002, "temp user", "temp dept");
        dao.insert(emp);
        
        //delete it
        dao.delete("2002");
        
        //check if deleted
        List<Employee> list = dao.getAll();
        for (Employee e : list) {
            if (e.getId() == 2002) {
                throw new RuntimeException("employee not deleted");
            }
        }
        
        System.out.println("delete employee test passed");
    }
    
    @Test
    public void testInsertMultipleEmployees() {
        System.out.println("testing insert multiple employees...");
        
        EmployeeDAO dao = new EmployeeDAO();
        
        //insert multiple employees
        Employee emp1 = new Employee(2003, "alice", "hr");
        Employee emp2 = new Employee(2004, "bob", "it");
        Employee emp3 = new Employee(2005, "charlie", "finance");
        
        dao.insert(emp1);
        dao.insert(emp2);
        dao.insert(emp3);
        
        List<Employee> list = dao.getAll();
        int count = 0;
        for (Employee e : list) {
            if (e.getId() == 2003 || e.getId() == 2004 || e.getId() == 2005) {
                count++;
            }
        }
        
        if (count < 3) {
            throw new RuntimeException("multiple insert failed");
        }
        
        System.out.println("insert multiple employees test passed");
    }

    @Test
    public void testEmployeeDataIntegrity() {
        System.out.println("testing employee data integrity...");
        
        EmployeeDAO dao = new EmployeeDAO();
        
        //test with different data types
        Employee emp1 = new Employee(2006, "mary johnson", "marketing");
        Employee emp2 = new Employee(2007, "david lee", "operations");
        
        dao.insert(emp1);
        dao.insert(emp2);
        
        List<Employee> list = dao.getAll();
        boolean found1 = false, found2 = false;
        
        for (Employee e : list) {
            if (e.getId() == 2006 && e.getName().equals("mary johnson")) {
                found1 = true;
            }
            if (e.getId() == 2007 && e.getName().equals("david lee")) {
                found2 = true;
            }
        }
        
        if (!found1 || !found2) {
            throw new RuntimeException("data integrity check failed");
        }
        
        System.out.println("employee data integrity test passed");
    }

    @Test
    public void testDeleteNonExistentEmployee() {
        System.out.println("testing delete non-existent employee...");
        
        EmployeeDAO dao = new EmployeeDAO();
        
        //try to delete employee that doesn't exist
        dao.delete("9999");
        
        //should not crash or cause problems
        List<Employee> list = dao.getAll();
        if (list == null) {
            throw new RuntimeException("database corrupted after invalid delete");
        }
        
        System.out.println("delete non-existent employee test passed");
    }

    @Test
    public void testLargeEmployeeList() {
        System.out.println("testing large employee list...");
        
        EmployeeDAO dao = new EmployeeDAO();
        
        //insert several employees to test performance
        for (int i = 2010; i < 2015; i++) {
            Employee emp = new Employee(i, "employee" + i, "dept" + i);
            dao.insert(emp);
        }
        
        List<Employee> list = dao.getAll();
        int count = 0;
        for (Employee e : list) {
            if (e.getId() >= 2010 && e.getId() < 2015) {
                count++;
            }
        }
        
        if (count < 5) {
            throw new RuntimeException("large list test failed");
        }
        
        System.out.println("large employee list test passed");
    }
} 