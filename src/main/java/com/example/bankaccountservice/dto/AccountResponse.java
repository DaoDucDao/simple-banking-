package com.example.bankaccountservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.bankaccountservice.dto.SubAccountDto.UserInfor;

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

    public AccountResponse(Long id, String accountHolderName, String accountNumber, BigDecimal balance,
            LocalDateTime createdAt, UserInfor user) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.createdAt = createdAt;
        this.user = user;
    }
}
