package com.godinho.empl_mgt_App.service;

import com.godinho.empl_mgt_App.dto.UserRegistrationDto;
import com.godinho.empl_mgt_App.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(UserRegistrationDto registrationDto);
    User findByEmail(String email);
//    List<User> getAllUsers();
//    Optional<User> getUserById(Long id);
//    void deleteUser(Long id);
//    Optional<User> findUserByEmail(String email);
}
