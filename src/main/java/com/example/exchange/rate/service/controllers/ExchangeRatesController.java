package com.example.exchange.rate.service.controllers;

import com.example.exchange.rate.service.dto.ExchangeRateDto;
import com.example.exchange.rate.service.dto.LatestRatesDto;
import com.example.exchange.rate.service.dto.SymbolsDto;
import com.example.exchange.rate.service.modal.ExchangeRate;
import com.example.exchange.rate.service.modal.Symbols;
import com.example.exchange.rate.service.service.ExchangeRateService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.exchange.rate.service.Constants.BASE_CURRENCY;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ExchangeRatesController {
    private ExchangeRateService exchangeRateService;

    @Autowired
    public void setExchangeRateService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping(value = "/symbols", produces = "application/json")
    public ResponseEntity<SymbolsDto> getListOfCurrencies() {
        log.info("[Request] List of SymbolsDto request received");

        Symbols currencies = exchangeRateService.getSymbols();
        SymbolsDto symbolsDto = SymbolsDto.from(currencies);

        log.info("[Response] List of Symbols: " + symbolsDto);

        return ResponseEntity.ok(symbolsDto);
    }

    @GetMapping(value = "/latest-rates", produces = "application/json")
    public ResponseEntity<LatestRatesDto> getExchangeRates() {
        return null;
    }

    @GetMapping(value = "/exchange-rates", produces = "application/json")
    public ResponseEntity<ExchangeRateDto> getConversionRate(
            @RequestParam(required = false, defaultValue = BASE_CURRENCY) String from,
            @RequestParam @NotBlank @NotNull String to,
            @RequestParam(required = false, defaultValue = "1.0") @Min(1) Double amount
    ) {
        log.info("[Request] Conversion request received with parameters: from={}, to={}, amount={}", from, to, amount);

        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(from, to, amount);
        ExchangeRateDto exchangeRateDto = ExchangeRateDto.from(exchangeRate);

        log.info("[Response] Conversion response: " + exchangeRateDto);

        return ResponseEntity.ok(exchangeRateDto);
    }

}
