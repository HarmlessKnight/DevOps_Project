package com.example.personal_finance_tracker.Services.impl;

import com.example.personal_finance_tracker.Exceptions.InvalidTransactionException;
import com.example.personal_finance_tracker.Models.Accounts;
import com.example.personal_finance_tracker.Models.TransactionTypes;
import com.example.personal_finance_tracker.Models.Transactions;
import com.example.personal_finance_tracker.Repositories.TransactionsRepository;
import com.example.personal_finance_tracker.Services.AccountService;
import com.example.personal_finance_tracker.Services.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceimpl implements TransactionService {

    private final TransactionsRepository transactionsRepository;
    private final AccountService accountService;

    public TransactionServiceimpl(TransactionsRepository transactionsRepository, AccountService accountService) {
        this.transactionsRepository = transactionsRepository;
        this.accountService = accountService;
    }

    @Override
    public Transactions addTransaction(long account_id, double amount, LocalDateTime currentdatetime, String description) {
        Accounts account = accountService.getAccountEntityById(account_id);

        return transactionsRepository.save(new Transactions(account,amount,currentdatetime,description));
    }

    @Override
    public Transactions deleteTransaction(long id) {
       Transactions transaction = transactionsRepository.findById(id).orElseThrow(() -> new InvalidTransactionException("Transaction not found"));
       transactionsRepository.delete(transaction);
       return transaction;
    }

    @Override
    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    @Override
    public List<Transactions> getTransactionsWithAccountId(Long accountId) {
        return transactionsRepository.findAllByAccount_Id(accountId);
    }
}
