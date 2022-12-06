package com.example.exchange.rate.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConversionRateInDto {
    @NotEmpty
    private String sourceCurrency;

    @NotEmpty
    private String targetCurrency;

    @NotNull
    @Min(1)
    private Double amount;
}
