package com.example.exchange.rate.service.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ExchangeRates {
    private Map<String, Double> exchangeRates;
    private String date;
}
