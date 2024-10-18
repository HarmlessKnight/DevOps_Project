package com.example.personal_finance_tracker.Services;

import com.example.personal_finance_tracker.DTOs.UserDTO;
import com.example.personal_finance_tracker.Models.Accounts;
import com.example.personal_finance_tracker.Models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    User addUser(UserDTO userDTO);
    User updateUser(Long id, UserDTO userDTO);
    User deleteUser(Long id);

    String VerifyUser(UserDTO userdto);
}
