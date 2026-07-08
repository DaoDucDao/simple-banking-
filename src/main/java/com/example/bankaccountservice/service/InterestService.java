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

    @Scheduled(fixedRate = 600000)
    public void applyMonthlyFee() {
        List<Account> listAccount = accountRepository.findByType(AccountType.CHECKINGS);

        listAccount.forEach(account -> {
            BigDecimal currentBalance = account.getBalance();
            BigDecimal monthlyFee = BigDecimal.valueOf(5.0);

            boolean compareResult = currentBalance.compareTo(monthlyFee) >= 0;

            if (compareResult) {
                BigDecimal substractedBalance = currentBalance.subtract(monthlyFee);
                account.setBalance(substractedBalance);
            } else {
                account.setBalance(BigDecimal.valueOf(0));

                BigDecimal debtAmount = monthlyFee.subtract(currentBalance);
                account.setDebt(debtAmount);
            }

            accountRepository.save(account);
        });
    }
}
