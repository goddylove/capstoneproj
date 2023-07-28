package com.godinho.empl_mgt_App.repo;

import com.godinho.empl_mgt_App.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
