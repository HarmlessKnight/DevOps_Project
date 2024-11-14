package com.example.personal_finance_tracker.Services;

import com.example.personal_finance_tracker.DTOs.AccountDTO;
import com.example.personal_finance_tracker.Models.AccountTypes;
import com.example.personal_finance_tracker.Models.Accounts;
import com.example.personal_finance_tracker.Models.Transactions;
import com.example.personal_finance_tracker.Models.User;

import java.util.List;
import java.util.Set;


public interface AccountService {

    public List<Accounts> getAllAccounts();
    public AccountDTO getAccountById(long id);
    public AccountDTO addAccount(Long id, AccountTypes type, User user , double balance, Set<Transactions> transactions);
    public AccountDTO updateAccountBalance(Long id,double balance);
    public AccountDTO deleteAccount(long id);

    Accounts getAccountEntityById(Long accountId);

    Accounts updateAccount(Accounts account);

    Accounts findById(Long accountId);

    List<Accounts> getAccountsWithUserId(Long id);
}
