package com.example.personal_finance_tracker.DTOs;

import com.example.personal_finance_tracker.Models.AccountTypes;

public class AccountDTO {
    private Long id;
    private double balance;
    private AccountTypes type;
    private Long userId;

    public AccountDTO(Long id,double balance, AccountTypes type, Long userId) {
        this.balance = balance;
        this.id = id;
        this.type = type;
        this.userId = userId;
    }
    public AccountDTO() {}


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountTypes getType() {
        return type;
    }

    public void setType(AccountTypes type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
