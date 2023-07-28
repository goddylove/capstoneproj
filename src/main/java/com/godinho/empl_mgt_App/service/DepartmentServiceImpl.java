package com.godinho.empl_mgt_App.service;

import com.godinho.empl_mgt_App.model.Department;
import com.godinho.empl_mgt_App.repo.DepartmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepo departmentRepository;

    public DepartmentServiceImpl(DepartmentRepo departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }
    public boolean doesDepartmentExist(String name) {
        // Check if the department with the given name already exists in the database
        return departmentRepository.existsByName(name);
    }
}
