package dao;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import employee.Employee;
import java.util.List;

//test for employee dao
public class EmployeeDAOTest {
    
    private EmployeeDAO dao;
    
    @Before
    public void setup() {
        dao = new EmployeeDAO();
    }
    
    @Test
    public void testInsertEmployee() {
        Employee emp = new Employee("EMP01", "John Doe", "IT");
        dao.insert(emp);
        
        List<Employee> employees = dao.getAll();
        boolean found = false;
        for (Employee e : employees) {
            if (e.getId().equals("EMP01")) {
                found = true;
                assertEquals("John Doe", e.getName());
                assertEquals("IT", e.getDepartment());
                break;
            }
        }
        assertTrue("employee should be found after insert", found);
    }
    
    @Test
    public void testGetAllEmployees() {
        List<Employee> employees = dao.getAll();
        assertNotNull("employee list should not be null", employees);
    }
    
    @Test
    public void testDeleteEmployee() {
        //add employee first
        Employee emp = new Employee("EMP02", "Jane Smith", "HR");
        dao.insert(emp);
        
        //delete it
        dao.delete("EMP02");
        
        //check if deleted
        List<Employee> employees = dao.getAll();
        for (Employee e : employees) {
            assertNotEquals("deleted employee should not exist", "EMP02", e.getId());
        }
    }
} 