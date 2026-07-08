package com.example.bankaccountservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.entity.AccountType;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByType(AccountType type);
}
