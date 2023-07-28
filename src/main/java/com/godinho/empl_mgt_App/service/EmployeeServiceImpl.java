package com.godinho.empl_mgt_App.service;

import com.godinho.empl_mgt_App.dto.EmployeeDTO;
import com.godinho.empl_mgt_App.model.Department;
import com.godinho.empl_mgt_App.model.Employee;
import com.godinho.empl_mgt_App.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepository;
    private final DepartmentService departmentService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepository, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    public List<Employee> getEmployeesByDepartmentId(Long id) {
        return employeeRepository.findByDepartmentId(id);
    }
    @Override
    public void createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());

        // Fetch the department by name from the database
        Department department = departmentService.getDepartmentByName(employeeDTO.getDepartmentName());

        if (department != null) {
            // Set the department for the new employee
            employee.setDepartment(department);
            employeeRepository.save(employee);
        } else {
            throw new IllegalArgumentException("Department with name " + employeeDTO.getDepartmentName() + " does not exist.");
        }
    }
    @Override
    public void updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(employeeId).orElse(null);
        if (existingEmployee == null) {
            throw new RuntimeException("Employee not found with ID: " + employeeId);
        }

        // Update the existingEmployee with the new details from employeeDTO
        existingEmployee.setName(employeeDTO.getName());

        Department department = departmentService.getDepartmentByName(employeeDTO.getDepartmentName());
        if (department != null) {
            existingEmployee.setDepartment(department);
        } else {
            throw new RuntimeException("Department not found with name: " + employeeDTO.getDepartmentName());
        }

        // No need to call employeeRepository.save(existingEmployee)
        // Since the entity is managed, changes will be automatically updated in the database.
    }
}