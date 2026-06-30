package com.example.bankaccountservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankaccountservice.dto.AmountRequest;
import com.example.bankaccountservice.dto.CreateAccountRequest;
import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request.getAccountHolderName(), request.getInitialDeposit());
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable Long id) {
        return accountService.getById(id);
    }

    @PostMapping("/{id}/deposit")
    public Account deposit(@Valid @RequestBody AmountRequest request, @PathVariable Long id) {
        return accountService.deposit(id, request.getAmount());
    }

    @PostMapping("/{id}/withdraw")
    public Account withdraw(@Valid @RequestBody AmountRequest request, @PathVariable Long id) {
        return accountService.withdraw(id, request.getAmount());
    }

}
