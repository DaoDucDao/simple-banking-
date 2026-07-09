package com.example.bankaccountservice.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.bankaccountservice.dto.AccountResponse;
import com.example.bankaccountservice.dto.SubAccountDto.UserInfor;
import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.entity.AccountStatus;
import com.example.bankaccountservice.entity.AccountType;
import com.example.bankaccountservice.exception.AccountNotActiveException;

public class AccountMapper {
    public static AccountResponse getAccountResponse(Account account) {
        Long userId = account.getUser().getId();
        String userEmail = account.getUser().getEmail();
        String userName = account.getUser().getEmail();

        UserInfor user = new UserInfor(userId, userEmail, userName);

        BigDecimal balance = account.getBalance();
        LocalDateTime createdAt = account.getCreatedAt();
        AccountType type = account.getType();

        AccountResponse result = new AccountResponse(userId, userName, userName, balance, createdAt, type, user);

        return result;
    }

    public static void checkStatus(Account account) {
        AccountStatus status = account.getStatus();

        if (status == AccountStatus.FROZEN)
            throw new AccountNotActiveException("Account is frozen!");

        if (status == AccountStatus.CLOSED)
            throw new AccountNotActiveException("Account is permanently closed!");
    }
}
