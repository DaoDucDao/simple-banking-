package com.example.bankaccountservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmountRequest {
    @NotNull(message = "Ammount is required!")
    @Positive(message = "Ammount must be positive!")
    private BigDecimal amount;
}
