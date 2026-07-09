package com.example.bankaccountservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.entity.AccountStatus;
import com.example.bankaccountservice.entity.AccountType;
import com.example.bankaccountservice.entity.Transaction;
import com.example.bankaccountservice.entity.User;
import com.example.bankaccountservice.exception.AccountNotActiveException;
import com.example.bankaccountservice.exception.AccountNotFoundException;
import com.example.bankaccountservice.exception.InsufficientFundsException;
import com.example.bankaccountservice.exception.UnauthorizedException;
import com.example.bankaccountservice.repository.AccountRepository;
import com.example.bankaccountservice.repository.TransactionRepository;
import com.example.bankaccountservice.util.AccountMapper;

@Service
public class AccountService {
    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;

    public AccountService(AccountRepository accountRepo, TransactionRepository transRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transRepo;
    }

    private void saveTransaction(String accountNumber, BigDecimal amount, String type) {
        LocalDateTime today = LocalDateTime.now();

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
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
        account.setInterestRate(BigDecimal.valueOf(3));
        account.setDebt(BigDecimal.valueOf(0));
        account.setStatus(AccountStatus.OPEN);

        return accountRepo.save(account);
    }

    public Account getById(Long id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found!"));

        return account;
    }

    public Account getByAccountNumber(String accountNumber) {
        Account account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found!"));
        return account;
    }

    public Account deposit(String accountNumber, BigDecimal ammount) {
        Account account = getByAccountNumber(accountNumber);

        AccountMapper.checkStatus(account);

        BigDecimal currentAmmount = account.getBalance();
        BigDecimal addedAmmount = currentAmmount.add(ammount);

        account.setBalance(addedAmmount);

        saveTransaction(accountNumber, ammount, "deposit");

        return accountRepo.save(account);
    }

    public Account withdraw(String accountNumber, BigDecimal ammount) {
        Account account = getByAccountNumber(accountNumber);

        AccountMapper.checkStatus(account);

        BigDecimal currentAmmount = account.getBalance();

        if (currentAmmount.compareTo(ammount) < 0)
            throw new InsufficientFundsException("Insufficient Fund!");

        BigDecimal substractedAmmount = currentAmmount.subtract(ammount);

        account.setBalance(substractedAmmount);

        saveTransaction(accountNumber, ammount, "withdraw");

        return accountRepo.save(account);
    }

    public Account changeInterest(String accountNumber, BigDecimal interestRate) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = getByAccountNumber(accountNumber);

        AccountMapper.checkStatus(account);

        boolean checkAuthen = account.getUser().getId().equals(currentUser.getId());

        if (!checkAuthen)
            throw new UnauthorizedException("You don't own this account");

        account.setInterestRate(interestRate);

        return accountRepo.save(account);
    }

    public Account adminChangeInterest(String accountNumber, BigDecimal interestRate) {
        Account account = getByAccountNumber(accountNumber);

        AccountMapper.checkStatus(account);

        account.setInterestRate(interestRate);
        return accountRepo.save(account);
    }

    public Account closeAccount(String accountNumber) {
        Account account = getByAccountNumber(accountNumber);

        account.setStatus(AccountStatus.CLOSED);
        return accountRepo.save(account);
    }

    public Account freezeAccount(String accountNumber) {
        Account account = getByAccountNumber(accountNumber);

        AccountStatus status = account.getStatus();
        if (status != AccountStatus.OPEN)
            throw new AccountNotActiveException("Account is not active");

        account.setStatus(AccountStatus.FROZEN);
        return accountRepo.save(account);
    }

    public Account unfreezeAccount(String accountNumber) {
        Account account = getByAccountNumber(accountNumber);

        AccountStatus status = account.getStatus();
        if (status != AccountStatus.FROZEN)
            throw new AccountNotActiveException("Account is not frozen");

        account.setStatus(AccountStatus.OPEN);
        return accountRepo.save(account);
    }
}
