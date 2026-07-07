package com.example.bankaccountservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    @NotBlank(message = "Account is required!")
    private String fromAccountNumber;

    @NotBlank(message = "Destination Account is required!")
    private String toAccountNumber;

    @NotNull(message = "Amount is required!")
    @Positive(message = "Ammount must be positive!")
    private BigDecimal amount;
}
