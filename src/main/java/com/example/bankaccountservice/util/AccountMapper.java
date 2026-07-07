package com.example.bankaccountservice.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.bankaccountservice.dto.AccountResponse;
import com.example.bankaccountservice.dto.SubAccountDto.UserInfor;
import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.entity.AccountType;

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
}
