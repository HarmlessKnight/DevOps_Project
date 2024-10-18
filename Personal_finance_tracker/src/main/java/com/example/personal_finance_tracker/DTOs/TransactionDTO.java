package com.example.personal_finance_tracker.DTOs;

import java.time.LocalDateTime;

public class TransactionDTO {
        private Long id;
        private double amount;
        private String description;
        private LocalDateTime timestamp;
        private Long accountId;

    public TransactionDTO(Long accountId, double amount, String description, Long id, LocalDateTime timestamp) {
        this.accountId = accountId;
        this.amount = amount;
        this.description = description;
        this.id = id;
        this.timestamp = timestamp;
    }
    public TransactionDTO() {}

    public Long getAccountId() {
            return accountId;
        }

        public void setAccountId(Long accountId) {
            this.accountId = accountId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }
    }
