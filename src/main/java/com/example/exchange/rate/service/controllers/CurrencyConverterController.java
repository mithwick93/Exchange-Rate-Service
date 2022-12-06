package com.example.exchange.rate.service.controllers;

import com.example.exchange.rate.service.dto.ConversionRateInDto;
import com.example.exchange.rate.service.dto.ConversionRateOutDto;
import com.example.exchange.rate.service.exceptions.CustomException;
import com.example.exchange.rate.service.exceptions.ErrorCode;
import com.example.exchange.rate.service.modal.ExchangeRates;
import com.example.exchange.rate.service.service.ExchangeRateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CurrencyConverterController {
    private ExchangeRateService exchangeRateService;

    @Autowired
    public void setExchangeRateService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @PostMapping("/rates")
    public ResponseEntity<ConversionRateOutDto> getConversionRate(@Valid @RequestBody ConversionRateInDto conversionRateInDto) {
        ExchangeRates exchangeRates = exchangeRateService.getExchangeRates();
        Map<String, Double> exchangeRatesMap = exchangeRates.getExchangeRates();

        if (!exchangeRatesMap.containsKey(conversionRateInDto.getSourceCurrency())) {
            throw new CustomException(ErrorCode.NO_SUCH_CURRENCY, "Source currency is invalid");
        }

        if (!exchangeRatesMap.containsKey(conversionRateInDto.getTargetCurrency())) {
            throw new CustomException(ErrorCode.NO_SUCH_CURRENCY, "Target currency is invalid");
        }

        double amount = conversionRateInDto.getAmount();
        double sourceCurrencyRate = exchangeRatesMap.get(conversionRateInDto.getSourceCurrency());
        double targetCurrencyRate = exchangeRatesMap.get(conversionRateInDto.getTargetCurrency());

        double resultDouble = (amount / sourceCurrencyRate) * targetCurrencyRate;

        BigDecimal resultBigDecimal = BigDecimal.valueOf(resultDouble);
        resultBigDecimal = resultBigDecimal.setScale(4, RoundingMode.HALF_UP);

        ConversionRateOutDto conversionRateOutDto = new ConversionRateOutDto();
        conversionRateOutDto.setSourceCurrency(conversionRateInDto.getSourceCurrency());
        conversionRateOutDto.setTargetCurrency(conversionRateInDto.getTargetCurrency());
        conversionRateOutDto.setResult(resultBigDecimal.doubleValue());
        conversionRateOutDto.setDate(exchangeRates.getDate());

        return ResponseEntity.ok(conversionRateOutDto);
    }
}
