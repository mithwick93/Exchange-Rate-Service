package com.example.exchange.rate.service.dto;

import com.example.exchange.rate.service.modal.ExchangeRate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeRateDto {
    private String from;
    private String to;

    private double amount;
    private double result;
    private String date;

    public static ExchangeRateDto from(ExchangeRate exchangeRate) {
        return new ExchangeRateDto(exchangeRate.getFrom(), exchangeRate.getTo(), exchangeRate.getAmount(), exchangeRate.getResult(), exchangeRate.getDate());
    }

}
