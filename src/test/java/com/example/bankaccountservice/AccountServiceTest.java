package com.example.bankaccountservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.entity.AccountStatus;
import com.example.bankaccountservice.exception.AccountNotActiveException;
import com.example.bankaccountservice.exception.AccountNotFoundException;
import com.example.bankaccountservice.exception.InsufficientFundsException;
import com.example.bankaccountservice.repository.AccountRepository;
import com.example.bankaccountservice.repository.TransactionRepository;
import com.example.bankaccountservice.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepo;

    @Mock
    private TransactionRepository transactionRepo;

    @InjectMocks
    private AccountService accountService;

    @Test
    void deposit_shouldIncreaseBalance() {
        Account fakeAccount = new Account();
        fakeAccount.setBalance(BigDecimal.valueOf(100));
        fakeAccount.setAccountNumber("855594");
        fakeAccount.setStatus(AccountStatus.OPEN);

        when(accountRepo.findByAccountNumber("855594")).thenReturn(Optional.of(fakeAccount));
        when(accountRepo.save(fakeAccount)).thenReturn(fakeAccount);

        Account result = accountService.deposit("855594", BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(150), result.getBalance());
    }

    @Test
    void deposit_withFronzenAccount_shouldThrowException() {
        Account fakeAccount = new Account();
        fakeAccount.setAccountNumber("855594");
        fakeAccount.setStatus(AccountStatus.FROZEN);

        when(accountRepo.findByAccountNumber("855594")).thenReturn(Optional.of(fakeAccount));

        assertThrows(AccountNotActiveException.class, () -> accountService.deposit("855594", BigDecimal.valueOf(50)));
    }

    @Test
    void withdraw_shouldDecreaseBalance() {
        Account fakeAccount = new Account();
        fakeAccount.setAccountNumber("855594");
        fakeAccount.setBalance(BigDecimal.valueOf(100));
        fakeAccount.setStatus(AccountStatus.OPEN);

        when(accountRepo.findByAccountNumber("855594")).thenReturn(Optional.of(fakeAccount));
        when(accountRepo.save(fakeAccount)).thenReturn(fakeAccount);

        Account result = accountService.withdraw("855594", BigDecimal.valueOf(40));

        assertEquals(BigDecimal.valueOf(60), result.getBalance());
    }

    @Test
    void withdraw_withInsufficientBalance_shouldThrowException() {
        Account fakeAccount = new Account();
        fakeAccount.setAccountNumber("855594");
        fakeAccount.setBalance(BigDecimal.valueOf(30));
        fakeAccount.setStatus(AccountStatus.OPEN);

        when(accountRepo.findByAccountNumber("855594")).thenReturn(Optional.of(fakeAccount));

        assertThrows(InsufficientFundsException.class, () -> accountService.withdraw("855594", BigDecimal.valueOf(100)));
    }

    @Test
    void getByAccountNumber_withNonExistentAccount_shouldThrowException() {
        when(accountRepo.findByAccountNumber("INVALID")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getByAccountNumber("INVALID"));
    }
}
