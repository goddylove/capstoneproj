package com.godinho.empl_mgt_App.repo;

import com.godinho.empl_mgt_App.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {

    Department findByName(String name);

    boolean existsByName(String name);
}
