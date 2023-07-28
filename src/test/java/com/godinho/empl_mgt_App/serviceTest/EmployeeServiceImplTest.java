package com.godinho.empl_mgt_App.serviceTest;

import com.godinho.empl_mgt_App.dto.EmployeeDTO;
import com.godinho.empl_mgt_App.model.Department;
import com.godinho.empl_mgt_App.model.Employee;
import com.godinho.empl_mgt_App.repo.EmployeeRepo;
import com.godinho.empl_mgt_App.service.DepartmentService;
import com.godinho.empl_mgt_App.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEmployees() {
        // Create a list of employees for the mock repository to return
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John Doe", "john.doe@example.com", new Department()));
        employees.add(new Employee(2L, "Jane Smith", "jane.smith@example.com", new Department()));

        // Mock the behavior of the employeeRepo.findAll() method to return the list of employees
        when(employeeRepo.findAll()).thenReturn(employees);

        // Call the service method
        List<Employee> result = employeeService.getAllEmployees();

        // Verify that the result is as expected
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());

        // Verify that the employeeRepo.findAll() method was called exactly once
        verify(employeeRepo, times(1)).findAll();
    }

    @Test
    public void testGetEmployeeById() {
        // Create an employee with a known ID for testing
        Employee employee = new Employee(1L, "John Doe", "john.doe@example.com", new Department());

        // Mock the behavior of the employeeRepo.findById() method to return the employee
        when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee));

        // Call the service method
        Employee result = employeeService.getEmployeeById(1L);

        // Verify that the result is as expected
        assertNotNull(result);
        assertEquals("John Doe", result.getName());

        // Verify that the employeeRepo.findById() method was called exactly once with the correct ID
        verify(employeeRepo, times(1)).findById(1L);
    }

    @Test
    public void testSaveEmployee() {
        // Create a new employee to save
        Employee employeeToSave = new Employee(null, "New Employee", "new.employee@example.com", new Department());

        // Call the service method to save the employee
        employeeService.saveEmployee(employeeToSave);

        // Verify that the employeeRepo.save() method was called exactly once with the employee to save
        verify(employeeRepo, times(1)).save(employeeToSave);
    }

    @Test
    public void testDeleteEmployee() {
        // Call the service method to delete an employee with a specific ID
        employeeService.deleteEmployee(1L);

        // Verify that the employeeRepo.deleteById() method was called exactly once with the correct ID
        verify(employeeRepo, times(1)).deleteById(1L);
    }

    @Test
    public void testGetEmployeesByDepartmentId() {
        // Create a department with a known ID for testing
        Department department = new Department(1L, "Department 1", new ArrayList<>());

        // Create a list of employees for the mock repository to return
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John Doe", "john.doe@example.com", department));
        employees.add(new Employee(2L, "Jane Smith", "jane.smith@example.com", department));

        // Mock the behavior of the employeeRepo.findByDepartmentId() method to return the list of employees
        when(employeeRepo.findByDepartmentId(1L)).thenReturn(employees);

        // Call the service method
        List<Employee> result = employeeService.getEmployeesByDepartmentId(1L);

        // Verify that the result is as expected
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());

        // Verify that the employeeRepo.findByDepartmentId() method was called exactly once with the correct ID
        verify(employeeRepo, times(1)).findByDepartmentId(1L);
    }

    @Test
    public void testCreateEmployee_ValidDepartment() {
        // Create a valid employeeDTO
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setDepartmentName("Department 1");

        // Create a department with a known name for testing
        Department department = new Department(1L, "Department 1", new ArrayList<>());

        // Mock the behavior of the departmentService.getDepartmentByName() method to return the department
        when(departmentService.getDepartmentByName("Department 1")).thenReturn(department);

        // Call the service method
        employeeService.createEmployee(employeeDTO);

        // Verify that the employeeRepo.save() method was called exactly once with the new employee
        verify(employeeRepo, times(1)).save(any(Employee.class));
    }

    @Test
    public void testCreateEmployee_InvalidDepartment() {
        // Create an employeeDTO with an invalid department name
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setDepartmentName("Non-existing Department");

        // Mock the behavior of the departmentService.getDepartmentByName() method to return null (department not found)
        when(departmentService.getDepartmentByName("Non-existing Department")).thenReturn(null);

        // Call the service method and assert that it throws an exception
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDTO));

        // Verify that the employeeRepo.save() method was not called (since an exception was thrown)
        verify(employeeRepo, never()).save(any(Employee.class));
    }
}