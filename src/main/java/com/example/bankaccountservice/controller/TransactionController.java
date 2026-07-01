package com.example.bankaccountservice.controller;

import com.example.bankaccountservice.entity.Transaction;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankaccountservice.service.TransactionService;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService service) {
        this.transactionService = service;
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }
}
