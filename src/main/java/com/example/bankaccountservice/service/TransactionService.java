package com.example.bankaccountservice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bankaccountservice.entity.Transaction;
import com.example.bankaccountservice.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository repo) {
        this.transactionRepository = repo;
    }

    public Page<Transaction> getTransactionByAccountNumber(String accountNumber, Pageable pageable) {
        return transactionRepository.findByAccountNumber(accountNumber, pageable);
    }

    public Transaction getTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        return transaction.orElseThrow(() -> new RuntimeException("Transaction Not Found!"));
    }
}
