package com.example.bankaccountservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.entity.Transaction;
import com.example.bankaccountservice.exception.AccountNotFoundException;
import com.example.bankaccountservice.exception.InsufficientFundsException;
import com.example.bankaccountservice.repository.AccountRepository;
import com.example.bankaccountservice.repository.TransactionRepository;

@Service
public class TransferService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    private void saveTransaction(String accountNumber, BigDecimal amount, String type) {
        LocalDateTime today = LocalDateTime.now();

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
        transaction.setAmount(amount);
        transaction.setCreateAt(today);
        transaction.setType(type);

        this.transactionRepository.save(transaction);
    }

    @Transactional
    public Account transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found!"));
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found!"));

        String fromNumber = fromAccount.getAccountNumber();
        String toNumber = toAccount.getAccountNumber();

        BigDecimal fromBalance = fromAccount.getBalance();
        if (fromBalance.compareTo(amount) < 0)
            throw new InsufficientFundsException("Insufficient fund!");

        BigDecimal toBalance = toAccount.getBalance();

        BigDecimal subtractedAmount = fromBalance.subtract(amount);
        fromAccount.setBalance(subtractedAmount);
        Account savedFrom = accountRepository.save(fromAccount);
        saveTransaction(fromNumber, amount, "transfer_out");

        BigDecimal addedAmount = toBalance.add(amount);
        toAccount.setBalance(addedAmount);
        accountRepository.save(toAccount);
        saveTransaction(toNumber, amount, "transfer_in");

        return savedFrom;
    }
}
