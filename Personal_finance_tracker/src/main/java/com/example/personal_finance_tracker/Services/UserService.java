package com.example.personal_finance_tracker.Services;

import com.example.personal_finance_tracker.DTOs.UserDTO;
import com.example.personal_finance_tracker.Models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    User addUser(UserDTO userDTO);
    User updateUser(Long id, UserDTO userDTO);
    User deleteUser(Long id);

    String VerifyUser(UserDTO userdto);

    User getUserByUsername(String username);
}
