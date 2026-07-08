package com.example.bankaccountservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankaccountservice.dto.AccountResponse;
import com.example.bankaccountservice.dto.AmountRequest;
import com.example.bankaccountservice.dto.CreateAccountRequest;
import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.entity.Transaction;
import com.example.bankaccountservice.service.AccountService;
import com.example.bankaccountservice.service.TransactionService;
import com.example.bankaccountservice.util.AccountMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account currentAccount = accountService.createAccount(request.getAccountHolderName(),
                request.getInitialDeposit(), request.getType());
        return AccountMapper.getAccountResponse(currentAccount);
    }

    @GetMapping("/{id}/id")
    public AccountResponse getById(@PathVariable Long id) {
        Account currentAccount = accountService.getById(id);
        return AccountMapper.getAccountResponse(currentAccount);
    }

    @GetMapping("/{accountNumber}/number")
    public AccountResponse getByAccountNumber(@PathVariable String accountNumber) {
        Account currentAccount = accountService.getByAccountNumber(accountNumber);
        return AccountMapper.getAccountResponse(currentAccount);
    }

    @PostMapping("/{accountNumber}/deposit")
    public AccountResponse deposit(@Valid @RequestBody AmountRequest request, @PathVariable String accountNumber) {
        Account currentAccount = accountService.deposit(accountNumber, request.getAmount());
        return AccountMapper.getAccountResponse(currentAccount);
    }

    @PostMapping("/{accountNumber}/withdraw")
    public AccountResponse withdraw(@Valid @RequestBody AmountRequest request, @PathVariable String accountNumber) {
        Account currentAccount = accountService.withdraw(accountNumber, request.getAmount());
        return AccountMapper.getAccountResponse(currentAccount);
    }

    @GetMapping("/{accountNumber}/transactions")
    public Page<Transaction> getTransactionByAccountNumber(@PathVariable String accountNumber, @RequestParam int page,
            @RequestParam int size) {
                Pageable pageable = PageRequest.of(page, size);
        return transactionService.getTransactionByAccountNumber(accountNumber, pageable);
    }
}
