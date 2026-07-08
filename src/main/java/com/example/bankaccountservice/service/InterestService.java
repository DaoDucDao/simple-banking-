package com.example.bankaccountservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.bankaccountservice.entity.Account;
import com.example.bankaccountservice.entity.AccountType;
import com.example.bankaccountservice.repository.AccountRepository;

@Service
public class InterestService {
    private AccountRepository accountRepository;

    public InterestService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void applyInterest() {
        List<Account> listAccount = accountRepository.findByType(AccountType.SAVINGS);

        listAccount.forEach(account -> {
            BigDecimal currentBalance = account.getBalance();
            BigDecimal currentInterestRate = account.getInterestRate();
            BigDecimal interestAmount = currentBalance.multiply(currentInterestRate).divide(BigDecimal.valueOf(100));
            BigDecimal addedBalance = currentBalance.add(interestAmount);

            account.setBalance(addedBalance);

            accountRepository.save(account);
        });
    }
}
