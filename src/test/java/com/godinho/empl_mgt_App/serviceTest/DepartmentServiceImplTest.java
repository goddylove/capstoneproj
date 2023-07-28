package com.godinho.empl_mgt_App.serviceTest;

import com.godinho.empl_mgt_App.model.Department;
import com.godinho.empl_mgt_App.repo.DepartmentRepo;
import com.godinho.empl_mgt_App.service.DepartmentServiceImpl;
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

public class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDepartments() {
        // Create a list of departments for the mock repository to return
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1L, "Department 1", new ArrayList<>()));
        departments.add(new Department(2L, "Department 2", new ArrayList<>()));

        // Mock the behavior of the departmentRepo.findAll() method to return the list of departments
        when(departmentRepo.findAll()).thenReturn(departments);

        // Call the service method
        List<Department> result = departmentService.getAllDepartments();

        // Verify that the result is as expected
        assertEquals(2, result.size());
        assertEquals("Department 1", result.get(0).getName());
        assertEquals("Department 2", result.get(1).getName());

        // Verify that the departmentRepo.findAll() method was called exactly once
        verify(departmentRepo, times(1)).findAll();
    }

    @Test
    public void testGetDepartmentById() {
        // Create a department with a known ID for testing
        Department department = new Department(1L, "Department 1", new ArrayList<>());

        // Mock the behavior of the departmentRepo.findById() method to return the department
        when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));

        // Call the service method
        Department result = departmentService.getDepartmentById(1L);

        // Verify that the result is as expected
        assertNotNull(result);
        assertEquals("Department 1", result.getName());

        // Verify that the departmentRepo.findById() method was called exactly once with the correct ID
        verify(departmentRepo, times(1)).findById(1L);
    }

    @Test
    public void testSaveDepartment() {
        // Create a new department to save
        Department departmentToSave = new Department(null, "New Department", new ArrayList<>());

        // Call the service method to save the department
        departmentService.saveDepartment(departmentToSave);

        // Verify that the departmentRepo.save() method was called exactly once with the department to save
        verify(departmentRepo, times(1)).save(departmentToSave);
    }

    @Test
    public void testDeleteDepartment() {
        // Call the service method to delete a department with a specific ID
        departmentService.deleteDepartment(1L);

        // Verify that the departmentRepo.deleteById() method was called exactly once with the correct ID
        verify(departmentRepo, times(1)).deleteById(1L);
    }

    @Test
    public void testGetDepartmentByName() {
        // Create a department with a known name for testing
        Department department = new Department(1L, "Department 1", new ArrayList<>());

        // Mock the behavior of the departmentRepo.findByName() method to return the department
        when(departmentRepo.findByName("Department 1")).thenReturn(department);

        // Call the service method
        Department result = departmentService.getDepartmentByName("Department 1");

        // Verify that the result is as expected
        assertNotNull(result);
        assertEquals(1L, result.getId());

        // Verify that the departmentRepo.findByName() method was called exactly once with the correct name
        verify(departmentRepo, times(1)).findByName("Department 1");
    }

    @Test
    public void testDoesDepartmentExist_ExistingDepartment() {
        // Create a department with a known name for testing
        Department department = new Department(1L, "Existing Department", new ArrayList<>());

        // Mock the behavior of the departmentRepo.existsByName() method to return true for an existing department
        when(departmentRepo.existsByName("Existing Department")).thenReturn(true);

        // Call the service method
        boolean result = departmentService.doesDepartmentExist("Existing Department");

        // Verify that the result is true since the department already exists
        assertTrue(result);

        // Verify that the departmentRepo.existsByName() method was called exactly once with the correct name
        verify(departmentRepo, times(1)).existsByName("Existing Department");
    }

    @Test
    public void testDoesDepartmentExist_NonExistingDepartment() {
        // Mock the behavior of the departmentRepo.existsByName() method to return false for a non-existing department
        when(departmentRepo.existsByName("Non-existing Department")).thenReturn(false);

        // Call the service method
        boolean result = departmentService.doesDepartmentExist("Non-existing Department");

        // Verify that the result is false since the department does not exist
        assertFalse(result);

        // Verify that the departmentRepo.existsByName() method was called exactly once with the correct name
        verify(departmentRepo, times(1)).existsByName("Non-existing Department");
    }
}
