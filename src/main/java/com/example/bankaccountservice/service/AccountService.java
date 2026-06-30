package com.example.bankaccountservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.exception.AccountNotFoundException;
import com.example.bankaccountservice.exception.InsufficientFundsException;
import com.example.bankaccountservice.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepo;

    public AccountService(AccountRepository repo) {
        this.accountRepo = repo;
    }

    public Account createAccount(String accountHolderName, BigDecimal initialBigDecimal) {
        Account account = new Account();

        LocalDateTime today = LocalDateTime.now();
        String randomId = UUID.randomUUID().toString();

        account.setAccountHolderName(accountHolderName);
        account.setBalance(initialBigDecimal);
        account.setCreatedAt(today);
        account.setAccountNumber(randomId);

        return accountRepo.save(account);
    }

    public Account getById(Long id) {
        Optional<Account> account = accountRepo.findById(id);

        return account.orElseThrow(() -> new AccountNotFoundException("Account Not Found!"));
    }

    public Account deposit(Long id, BigDecimal ammount) {
        Account account = getById(id);

        BigDecimal currentAmmount = account.getBalance();
        BigDecimal addedAmmount = currentAmmount.add(ammount);

        account.setBalance(addedAmmount);

        return accountRepo.save(account);
    }

    public Account withdraw(Long id, BigDecimal ammount) {
        Account account = getById(id);

        BigDecimal currentAmmount = account.getBalance();

        if (currentAmmount.compareTo(ammount) < 0)
            throw new InsufficientFundsException("Insufficient Fund!");

        BigDecimal substactedAmmount = currentAmmount.subtract(ammount);

        account.setBalance(substactedAmmount);

        return accountRepo.save(account);
    }
}
