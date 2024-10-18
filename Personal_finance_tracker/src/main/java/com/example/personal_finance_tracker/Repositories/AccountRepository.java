package com.example.personal_finance_tracker.Repositories;

import com.example.personal_finance_tracker.Models.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,Long> {

    List<Accounts> findByUser_Id(Long id);
}
