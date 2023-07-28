package com.godinho.empl_mgt_App.repo;

import com.godinho.empl_mgt_App.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
