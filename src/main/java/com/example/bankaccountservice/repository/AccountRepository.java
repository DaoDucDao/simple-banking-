package com.example.bankaccountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bankaccountservice.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
