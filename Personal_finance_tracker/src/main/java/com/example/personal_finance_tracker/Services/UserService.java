package com.example.personal_finance_tracker.Services;

import com.example.personal_finance_tracker.DTOs.UserDTO;
import com.example.personal_finance_tracker.Models.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    User addUser(UserDTO userDTO);
    User updateUser(Long id, UserDTO userDTO);
    User deleteUser(Long id);

    Map<String, Object> VerifyUser(UserDTO userdto);

    User getUserByUsername(String username);
}
