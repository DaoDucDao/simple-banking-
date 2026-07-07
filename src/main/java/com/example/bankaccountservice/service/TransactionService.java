package com.example.bankaccountservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.bankaccountservice.entity.Transaction;
import com.example.bankaccountservice.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository repo) {
        this.transactionRepository = repo;
    }

    public List<Transaction> getTransactionByAccountId(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }

    public Transaction getTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        return transaction.orElseThrow(() -> new RuntimeException("Transaction Not Found!"));
    }
}
