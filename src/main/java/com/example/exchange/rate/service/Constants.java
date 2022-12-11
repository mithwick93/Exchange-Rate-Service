package com.example.exchange.rate.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Constants {
    public static final String BASE_CURRENCY = "EUR";

    public static final int PRECISION = 4;

    public static final BigDecimal BASE_CURRENCY_RATE = new BigDecimal("1.00").setScale(PRECISION, RoundingMode.HALF_UP);
}
