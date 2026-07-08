package com.example.bankaccountservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterestRateRequest {
    @NotNull(message = "Rate is required!")
    @Positive(message = "Rate must be positive!")
    private BigDecimal interestRate;
}
