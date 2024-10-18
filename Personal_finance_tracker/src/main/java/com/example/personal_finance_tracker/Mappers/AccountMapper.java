package com.example.personal_finance_tracker.Mappers;

import com.example.personal_finance_tracker.DTOs.AccountDTO;
import com.example.personal_finance_tracker.Models.Accounts;
import com.example.personal_finance_tracker.Models.User;
import com.example.personal_finance_tracker.Repositories.UserRepository;

public class AccountMapper {

    private final UserRepository userRepository;

    public AccountMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static AccountDTO toDTO(Accounts account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setType(account.getType());
        dto.setBalance(account.getBalance());
        dto.setUserId(account.getUser().getId());
        return dto;
    }

    public static Accounts toEntity(AccountDTO dto,UserRepository userRepository) {
        Accounts account = new Accounts();

        if (dto.getId() != null) {
            account.setId(dto.getId());
        }

        account.setType(dto.getType());
        account.setBalance(dto.getBalance());
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        account.setUser(user);
        return account;
    }
}
