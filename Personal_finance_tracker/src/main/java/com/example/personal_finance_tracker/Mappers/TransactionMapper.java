package com.example.personal_finance_tracker.Mappers;

import com.example.personal_finance_tracker.DTOs.TransactionDTO;
import com.example.personal_finance_tracker.Models.Transactions;
import com.example.personal_finance_tracker.Models.Accounts;
import com.example.personal_finance_tracker.Repositories.AccountRepository;

public class TransactionMapper {

    private final AccountRepository accountRepository;

    public TransactionMapper(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public static TransactionDTO toDTO(Transactions transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setDescription(transaction.getDescription());
        dto.setTimestamp(transaction.getTimestamp());
        dto.setAccountId(transaction.getAccount().getId());
        return dto;
    }

    public static Transactions toEntity(TransactionDTO dto, AccountRepository accountRepository) {
        Transactions transaction = new Transactions();

        if (dto.getId() != null) {
            transaction.setId(dto.getId());
        }

        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setTimestamp(dto.getTimestamp());

        Accounts account = accountRepository.findById(dto.getAccountId()).orElse(null);
        transaction.setAccount(account);

        return transaction;
    }
}
