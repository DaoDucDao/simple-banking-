package com.example.bankaccountservice.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankaccountservice.dto.AccountResponse;
import com.example.bankaccountservice.dto.InterestRateRequest;
import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.service.AccountService;
import com.example.bankaccountservice.util.AccountMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/api")
public class AdminController {
    private final AccountService accountService;

    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PatchMapping("/account/{accountNumber}/interest")
    public AccountResponse changeInterestRate(@PathVariable String accountNumber,
            @Valid @RequestBody InterestRateRequest request) {
        Account account = accountService.changeInterest(accountNumber, request.getInterestRate());

        return AccountMapper.getAccountResponse(account);
    }
}
