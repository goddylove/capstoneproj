package com.godinho.empl_mgt_App.service;

import com.godinho.empl_mgt_App.model.Department;
import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentById(Long id);
    void saveDepartment(Department department);
    void deleteDepartment(Long id);

    Department getDepartmentByName(String name);

    boolean doesDepartmentExist(String name);
}