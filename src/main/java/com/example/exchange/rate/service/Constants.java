package com.example.exchange.rate.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Constants {
    public static final String BASE_CURRENCY = "EUR";

    public static final BigDecimal BASE_CURRENCY_RATE = new BigDecimal("1.00").setScale(4, RoundingMode.HALF_UP);
}
