package com.example.exchange.rate.service.controllers;

import com.example.exchange.rate.service.dto.ConversionRateOutDto;
import com.example.exchange.rate.service.dto.Currencies;
import com.example.exchange.rate.service.exceptions.CustomException;
import com.example.exchange.rate.service.exceptions.ErrorCode;
import com.example.exchange.rate.service.modal.ExchangeRates;
import com.example.exchange.rate.service.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CurrencyConverterController {
    private ExchangeRateService exchangeRateService;

    @Autowired
    public void setExchangeRateService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping(value = "/currencies", produces = "application/json")
    public ResponseEntity<Currencies> getListOfCurrencies() {
        log.info("[Request] List of Currencies request received");

        ExchangeRates exchangeRates = exchangeRateService.getExchangeRates();

        Currencies currencies = new Currencies(exchangeRates.getExchangeRates().keySet().stream().toList());
        return ResponseEntity.ok(currencies);
    }

    @GetMapping(value = "/exchange-rates", produces = "application/json")
    public ResponseEntity<ConversionRateOutDto> getConversionRate(
            @RequestParam(required = false, defaultValue = "EUR") String from,
            @RequestParam String to, @RequestParam(required = false,
            defaultValue = "1.0") Double amount
    ) {
        log.info("[Request] Conversion request received with parameters: from={}, to={}, amount={}", from, to, amount);

        ExchangeRates exchangeRates = exchangeRateService.getExchangeRates();
        Map<String, BigDecimal> exchangeRatesMap = exchangeRates.getExchangeRates();

        if (!"EUR".equals(from) && !exchangeRatesMap.containsKey(from)) {
            throw new CustomException(ErrorCode.NO_SUCH_CURRENCY, "Source currency is invalid");
        }

        if (!exchangeRatesMap.containsKey(to)) {
            throw new CustomException(ErrorCode.NO_SUCH_CURRENCY, "Target currency is invalid");
        }

        ConversionRateOutDto conversionRateOutDto = new ConversionRateOutDto();

        BigDecimal amountBigDecimal = BigDecimal.valueOf(amount);

        BigDecimal sourceCurrencyRate = "EUR".equals(from) ? new BigDecimal("1.00").setScale(4, RoundingMode.HALF_UP) : exchangeRatesMap.get(from);
        BigDecimal targetCurrencyRate = exchangeRatesMap.get(to);

        BigDecimal resultDouble = (amountBigDecimal.divide(sourceCurrencyRate, RoundingMode.HALF_UP)).multiply(targetCurrencyRate);
        resultDouble = resultDouble.setScale(4, RoundingMode.HALF_UP);

        conversionRateOutDto.setSourceCurrency(from);
        conversionRateOutDto.setTargetCurrency(to);
        conversionRateOutDto.setResult(resultDouble.doubleValue());
        conversionRateOutDto.setDate(exchangeRates.getDate());

        log.info("[Response] Conversion response: " + conversionRateOutDto);

        return ResponseEntity.ok(conversionRateOutDto);
    }

}
