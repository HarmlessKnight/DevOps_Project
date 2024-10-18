package com.example.personal_finance_tracker.Mappers;


import com.example.personal_finance_tracker.DTOs.UserDTO;
import com.example.personal_finance_tracker.Models.User;
import com.example.personal_finance_tracker.Repositories.AccountRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();

        if (dto.getId() != null) {
            user.setId(dto.getId());
        }

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());


        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }

        return user;
    }
}