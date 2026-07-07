package com.example.bankaccountservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankaccountservice.dto.AccountResponse;
import com.example.bankaccountservice.dto.TransferRequest;
import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.service.TransferService;
import com.example.bankaccountservice.util.AccountMapper;

import jakarta.validation.Valid;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public AccountResponse transfer(@Valid @RequestBody TransferRequest request) {
        String fromAccountNumber = request.getFromAccountNumber();
        String toAccountNumber = request.getToAccountNumber();
        BigDecimal amount = request.getAmount();

        Account sourceAccount = transferService.transfer(fromAccountNumber, toAccountNumber, amount);

        return AccountMapper.getAccountResponse(sourceAccount);
    }

}
