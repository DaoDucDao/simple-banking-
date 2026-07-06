package com.example.bankaccountservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankaccountservice.dto.AccountResponse;
import com.example.bankaccountservice.dto.AmountRequest;
import com.example.bankaccountservice.dto.CreateAccountRequest;
import com.example.bankaccountservice.dto.SubAccountDto.UserInfor;
import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.entity.Transaction;
import com.example.bankaccountservice.service.AccountService;
import com.example.bankaccountservice.service.TransactionService;

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
                request.getInitialDeposit());
        return getResponse(currentAccount);
    }

    private AccountResponse getResponse(Account currentAccount) {
        Long userId = currentAccount.getUser().getId();
        String userEmail = currentAccount.getUser().getEmail();
        String userName = currentAccount.getUser().getUsername();

        UserInfor currentUser = new UserInfor(userId, userEmail, userName);

        return new AccountResponse(currentAccount.getId(), currentAccount.getAccountHolderName(),
                currentAccount.getAccountNumber(), currentAccount.getBalance(), currentAccount.getCreatedAt(),
                currentUser);
    }

    @GetMapping("/{id}")
    public AccountResponse getById(@PathVariable Long id) {
        Account currentAccount = accountService.getById(id);
        return getResponse(currentAccount);
    }

    @PostMapping("/{id}/deposit")
    public AccountResponse deposit(@Valid @RequestBody AmountRequest request, @PathVariable Long id) {
        Account currentAccount = accountService.deposit(id, request.getAmount());
        return getResponse(currentAccount);
    }

    @PostMapping("/{id}/withdraw")
    public AccountResponse withdraw(@Valid @RequestBody AmountRequest request, @PathVariable Long id) {
        Account currentAccount = accountService.withdraw(id, request.getAmount());
        return getResponse(currentAccount);
    }

    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactionByAccountId(@PathVariable Long id) {
        return transactionService.getTransactionByAccountId(id);
    }

}
