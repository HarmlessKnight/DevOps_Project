package com.example.personal_finance_tracker.Controllers;

import com.example.personal_finance_tracker.DTOs.AccountDTO;
import com.example.personal_finance_tracker.Exceptions.InvalidAccountException;
import com.example.personal_finance_tracker.Mappers.AccountMapper;
import com.example.personal_finance_tracker.Models.Accounts;
import com.example.personal_finance_tracker.Repositories.AccountRepository;
import com.example.personal_finance_tracker.Repositories.UserRepository;
import com.example.personal_finance_tracker.Services.AccountService;
import com.example.personal_finance_tracker.Services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;
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


    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        return accountService.getAllAccounts().stream()
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        Optional<Accounts> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            return ResponseEntity.ok(AccountMapper.toDTO(accountOptional.get()));
        } else {
            throw new InvalidAccountException("Account not found with id " + id);
        }
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

    @GetMapping("/user/{userId}")
    public List<AccountDTO> getAccountsByUserId(@PathVariable Long userId) {
        return accountRepository.findByUser_Id(userId).stream()
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());
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
