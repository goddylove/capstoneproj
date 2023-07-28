package com.godinho.empl_mgt_App.repo;

import com.godinho.empl_mgt_App.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);
    List<Employee> findByDepartmentId(Long departmentId);
}
