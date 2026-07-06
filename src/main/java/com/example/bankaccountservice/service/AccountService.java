package com.example.bankaccountservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.entity.AccountType;
import com.example.bankaccountservice.entity.Transaction;
import com.example.bankaccountservice.entity.User;
import com.example.bankaccountservice.exception.AccountNotFoundException;
import com.example.bankaccountservice.exception.InsufficientFundsException;
import com.example.bankaccountservice.repository.AccountRepository;
import com.example.bankaccountservice.repository.TransactionRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;

    public AccountService(AccountRepository accountRepo, TransactionRepository transRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transRepo;
    }

    private void saveTransaction(Long id, BigDecimal amount, String type) {
        LocalDateTime today = LocalDateTime.now();

        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setCreateAt(today);
        transaction.setType(type);

        this.transactionRepo.save(transaction);
    }

    public Account createAccount(String accountHolderName, BigDecimal initialBigDecimal, AccountType type) {
        Account account = new Account();

        LocalDateTime today = LocalDateTime.now();
        String randomId = UUID.randomUUID().toString();

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        account.setAccountHolderName(accountHolderName);
        account.setBalance(initialBigDecimal);
        account.setCreatedAt(today);
        account.setAccountNumber(randomId);
        account.setUser(currentUser);
        account.setType(type);

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

        saveTransaction(id, ammount, "deposit");

        return accountRepo.save(account);
    }

    public Account withdraw(Long id, BigDecimal ammount) {
        Account account = getById(id);

        BigDecimal currentAmmount = account.getBalance();

        if (currentAmmount.compareTo(ammount) < 0)
            throw new InsufficientFundsException("Insufficient Fund!");

        BigDecimal substactedAmmount = currentAmmount.subtract(ammount);

        account.setBalance(substactedAmmount);

        saveTransaction(id, ammount, "withdraw");

        return accountRepo.save(account);
    }
}
