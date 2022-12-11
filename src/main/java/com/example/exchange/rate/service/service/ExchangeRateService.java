package com.example.exchange.rate.service.service;

import com.example.exchange.rate.service.exceptions.CustomException;
import com.example.exchange.rate.service.exceptions.ErrorCode;
import com.example.exchange.rate.service.modal.BaseExchangeRates;
import com.example.exchange.rate.service.modal.ExchangeRate;
import com.example.exchange.rate.service.modal.LatestRates;
import com.example.exchange.rate.service.modal.Symbols;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static com.example.exchange.rate.service.Constants.BASE_CURRENCY;
import static com.example.exchange.rate.service.Constants.BASE_CURRENCY_RATE;

@Service
public class ExchangeRateService {
    private ExchangeRateSourceService exchangeRateSourceService;

    @Autowired
    public void setExchangeRateService(ExchangeRateSourceService exchangeRateSourceService) {
        this.exchangeRateSourceService = exchangeRateSourceService;
    }

    public Symbols getSymbols() {
        BaseExchangeRates baseExchangeRates = exchangeRateSourceService.getBaseExchangeRates();

        return new Symbols(baseExchangeRates.getExchangeRates().keySet().stream().toList(), baseExchangeRates.getDate());
    }

    public LatestRates getLatestRates() {
        BaseExchangeRates baseExchangeRates = exchangeRateSourceService.getBaseExchangeRates();
        return new LatestRates(BASE_CURRENCY, baseExchangeRates.getDate(), baseExchangeRates.getExchangeRates());
    }

    public ExchangeRate getExchangeRate(String from, String to, double amount) {
        BaseExchangeRates baseExchangeRates = exchangeRateSourceService.getBaseExchangeRates();
        Map<String, BigDecimal> exchangeRatesMap = baseExchangeRates.getExchangeRates();

        if (!BASE_CURRENCY.equals(from) && !exchangeRatesMap.containsKey(from)) {
            throw new CustomException(ErrorCode.NO_SUCH_CURRENCY, "Source currency is invalid");
        }

        if (!BASE_CURRENCY.equals(to) && !exchangeRatesMap.containsKey(to)) {
            throw new CustomException(ErrorCode.NO_SUCH_CURRENCY, "Target currency is invalid");
        }

        if (from.equals(to)) {
            return new ExchangeRate(from, to, amount, amount, baseExchangeRates.getDate());
        }

        BigDecimal amountBigDecimal = BigDecimal.valueOf(amount);
        BigDecimal sourceCurrencyRate = BASE_CURRENCY.equals(from) ? BASE_CURRENCY_RATE : exchangeRatesMap.get(from);
        BigDecimal targetCurrencyRate = BASE_CURRENCY.equals(to) ? BASE_CURRENCY_RATE : exchangeRatesMap.get(to);

        BigDecimal resultDouble = amountBigDecimal.divide(sourceCurrencyRate, RoundingMode.HALF_UP).multiply(targetCurrencyRate);
        resultDouble = resultDouble.setScale(4, RoundingMode.HALF_UP);

        return new ExchangeRate(from, to, amount, resultDouble.doubleValue(), baseExchangeRates.getDate());
    }
}
