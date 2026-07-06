package com.example.bankaccountservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.bankaccountservice.dto.SubAccountDto.UserInfor;
import com.example.bankaccountservice.entity.AccountType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {
    private Long id;
    private String accountHolderName;
    private String accountNumber;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private UserInfor user;
    private AccountType type;

    public AccountResponse(Long id, String accountHolderName, String accountNumber, BigDecimal balance,
            LocalDateTime createdAt, AccountType type, UserInfor user) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.createdAt = createdAt;
        this.type = type;
        this.user = user;
    }
}
