package com.example.exchange.rate.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionRateOutDto {
    private String sourceCurrency;
    private String targetCurrency;
    private Double result;
    private String date;
}
