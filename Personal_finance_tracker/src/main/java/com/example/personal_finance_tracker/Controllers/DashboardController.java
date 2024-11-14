package com.example.personal_finance_tracker.Controllers;

import com.example.personal_finance_tracker.Models.Accounts;
import com.example.personal_finance_tracker.Models.Transactions;
import com.example.personal_finance_tracker.Models.User;
import com.example.personal_finance_tracker.Services.AccountService;
import com.example.personal_finance_tracker.Services.TransactionService;
import com.example.personal_finance_tracker.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/api")
public class DashboardController {

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public DashboardController(UserService userService, AccountService accountService, TransactionService transactionService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardData(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated())
        {
            String username = authentication.getName();
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            Map<String, Object> response = new HashMap<>();
            User user = userService.getUserByUsername(username);

            List<Accounts> accounts = accountService.getAccountsWithUserId(user.getId());
            List<Map<String, Object>> accountDetails = new ArrayList<>();

            for (Accounts account : accounts)
            {
                Map<String, Object> accountData = new HashMap<>();
                accountData.put("accountId", account.getId());
                accountData.put("type", account.getType());
                accountData.put("balance", account.getBalance());
                List<Transactions> transactions = transactionService.getTransactionsWithAccountId(account.getId());
                accountData.put("transactions", transactions);
                accountDetails.add(accountData);
            }
            response.put("username", username);
            response.put("roles", roles);
            response.put("accounts", accountDetails);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
    }

}
