package com.example.personal_finance_tracker.Controllers;


import com.example.personal_finance_tracker.DTOs.TransactionDTO;
import com.example.personal_finance_tracker.Exceptions.InvalidAccountException;
import com.example.personal_finance_tracker.Models.Accounts;
import com.example.personal_finance_tracker.Models.Transactions;
import com.example.personal_finance_tracker.Models.User;
import com.example.personal_finance_tracker.Repositories.AccountRepository;
import com.example.personal_finance_tracker.Repositories.UserRepository;
import com.example.personal_finance_tracker.Services.AccountService;
import com.example.personal_finance_tracker.Services.TransactionService;
import com.example.personal_finance_tracker.Services.UserService;
import com.example.personal_finance_tracker.config.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.personal_finance_tracker.Mappers.TransactionMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/transactions")

public class TransactionsController {

    private final TransactionService transactionsService;
    private final AccountService accountService;
    private final UserService userService;

    public TransactionsController(TransactionService transactionsService, AccountService accountRepository, UserService userRepository, AccountRepository accountRepository1, UserRepository userRepository1, AccountService accountService, UserService userService) {
        this.transactionsService = transactionsService;
        this.accountService = accountService;
        this.userService = userService;
    }


    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(@PathVariable Long accountId) {

        if (!SecurityUtils.isAuthenticated())
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        }

        Accounts account = accountService.findById(accountId);

        SecurityUtils.CheckOwnership(account.getUser().getId());

        List<Transactions> transactions = transactionsService.getTransactionsWithAccountId(accountId);

        List<TransactionDTO> transactionDTOs = transactions.stream().map(TransactionMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(transactionDTOs);
    }


}

