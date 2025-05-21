package com.example.personal_finance_tracker;

import io.unlogged.Unlogged;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersonalFinanceTrackerApplication {

    @Unlogged
    public static void main(String[] args) {
        SpringApplication.run(PersonalFinanceTrackerApplication.class, args);
    }

}
