package com.godinho.empl_mgt_App.service;

import com.godinho.empl_mgt_App.dto.EmployeeDTO;
import com.godinho.empl_mgt_App.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    void saveEmployee(Employee employee);
    void deleteEmployee(Long id);
    List<Employee> getEmployeesByDepartmentId(Long id);
    void createEmployee(EmployeeDTO employeeDTO);

    void updateEmployee(Long employeeId, EmployeeDTO employeeDTO);
}