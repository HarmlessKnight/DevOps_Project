package com.example.personal_finance_tracker.Repositories;

import com.example.personal_finance_tracker.Models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions,Long> {
    List<Transactions> findAllByAccount_Id(Long id);
}
