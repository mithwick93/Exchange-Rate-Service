package com.example.exchange.rate.service.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class LatestRates {
    private String base;
    private String date;
    private Map<String, BigDecimal> exchangeRates;

}
