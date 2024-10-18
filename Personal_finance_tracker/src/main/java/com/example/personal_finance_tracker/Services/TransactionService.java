package com.example.personal_finance_tracker.Services;

import com.example.personal_finance_tracker.Models.TransactionTypes;
import com.example.personal_finance_tracker.Models.Transactions;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TransactionService {

    public Transactions addTransaction(long account_id, double amount, LocalDateTime currentdatetime, String description);
    public Transactions deleteTransaction(long id);
    public List<Transactions> getAllTransactions();
    public List<Transactions> getTransactionsWithAccountId(Long accountId);
}
