package com.example.personal_finance_tracker.Controllers;

import com.example.personal_finance_tracker.DTOs.AccountDTO;
import com.example.personal_finance_tracker.Exceptions.InvalidAccountException;
import com.example.personal_finance_tracker.Mappers.AccountMapper;
import com.example.personal_finance_tracker.Models.Accounts;
import com.example.personal_finance_tracker.Repositories.AccountRepository;
import com.example.personal_finance_tracker.Repositories.UserRepository;
import com.example.personal_finance_tracker.Services.AccountService;
import com.example.personal_finance_tracker.Services.UserService;
import com.example.personal_finance_tracker.config.SecurityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    //use services fix later
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public AccountController(AccountRepository accountRepository, AccountService accountService, UserService userService, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {

        Accounts account = accountService.findById(id);
        SecurityUtils.CheckOwnership(account.getUser().getId());
        return ResponseEntity.ok(AccountMapper.toDTO(account));

    }

    @PostMapping("/NewAccount")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {

        Accounts account = new Accounts();

        account.setType(accountDTO.getType());
        account.setUser(userService.getUserById(accountDTO.getUserId()));
        account.setBalance(accountDTO.getBalance());
        accountRepository.save(account);

        return ResponseEntity.ok(AccountMapper.toDTO(account));
    }

    @GetMapping()
    public List<AccountDTO> getUserAccounts() {
        Long userId = SecurityUtils.getCurrentUserId();
        return accountService.getAccountsWithUserId(userId).stream().map(AccountMapper::toDTO).collect(Collectors.toList());
    }


    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        if (!accountRepository.existsById(id)) {
            throw new InvalidAccountException("Account not found with id: " + id);
        }

        accountDTO.setId(id);
        Accounts account = AccountMapper.toEntity(accountDTO, userRepository);
        Accounts updatedAccount = accountService.updateAccount(account);

        return ResponseEntity.ok(AccountMapper.toDTO(updatedAccount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
