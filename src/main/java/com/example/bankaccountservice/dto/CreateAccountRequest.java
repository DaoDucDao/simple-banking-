package com.example.bankaccountservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class CreateAccountRequest {
    @NotBlank(message = "Account holder name is required!")
    private String accountHolderName;

    @NotNull(message = "Initial deposit is required!")
    @PositiveOrZero(message = "Initial deposit cannot be negative!")
    private BigDecimal initialDeposit;
}
