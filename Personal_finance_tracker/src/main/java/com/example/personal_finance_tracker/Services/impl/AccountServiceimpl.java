package com.example.personal_finance_tracker.Services.impl;

import com.example.personal_finance_tracker.DTOs.AccountDTO;
import com.example.personal_finance_tracker.Exceptions.InvalidAccountException;
import com.example.personal_finance_tracker.Models.AccountTypes;
import com.example.personal_finance_tracker.Models.Accounts;
import com.example.personal_finance_tracker.Models.Transactions;
import com.example.personal_finance_tracker.Models.User;
import com.example.personal_finance_tracker.Repositories.AccountRepository;
import com.example.personal_finance_tracker.Services.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AccountServiceimpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceimpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public List<Accounts> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public AccountDTO getAccountById(long id) {
        Accounts account = accountRepository.findById(id)
                .orElseThrow(() -> new InvalidAccountException("Account not found"));

        return new AccountDTO(
                account.getId(),
                account.getBalance(),
                account.getType(),
                account.getUser().getId()
        );
    }


    @Override
    public AccountDTO addAccount(Long id, AccountTypes type, User user, double balance, Set<Transactions> transactions) {
        Accounts account = new Accounts(id, type, user, balance, transactions);
        Accounts createdAccount = accountRepository.save(account); // Save the entity

        return new AccountDTO(
                account.getId(),
                account.getBalance(),
                account.getType(),
                account.getUser().getId()
        );
    }


    @Override
    public AccountDTO updateAccountBalance(Long id, double balance) {
        Accounts account = accountRepository.findById(id)
                .orElseThrow(() -> new InvalidAccountException("Account not found"));

        account.setBalance(balance);
        Accounts updatedAccount = accountRepository.save(account);

        return new AccountDTO(
                updatedAccount.getId(),
                updatedAccount.getBalance(),
                updatedAccount.getType(),
                updatedAccount.getUser().getId()
        );
    }



    @Override
    public AccountDTO deleteAccount(long id) {
        Accounts account = accountRepository.findById(id)
                .orElseThrow(() -> new InvalidAccountException("Account not found"));

        accountRepository.delete(account);

        return new AccountDTO(
                account.getId(),
                account.getBalance(),
                account.getType(),
                account.getUser().getId()
        );
    }

    @Override
    public Accounts getAccountEntityById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new InvalidAccountException("Account not found"));
    }

    @Override
    public Accounts updateAccount(Accounts account) {
        return accountRepository.save(account); // This will update if the ID exists
    }

    @Override
    public Accounts findById(Long accountId) {
        Accounts account = accountRepository.findById(accountId).orElseThrow(() -> new InvalidAccountException("Account not found"));
        return account;
    }

    @Override
    public List<Accounts> getAccountsWithUserId(Long id) {
        return accountRepository.findByUser_Id(id);
    }


}
