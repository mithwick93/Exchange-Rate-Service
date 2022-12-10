package com.example.exchange.rate.service.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class ExchangeRates {
    private Map<String, BigDecimal> exchangeRates;
    private String date;
}
