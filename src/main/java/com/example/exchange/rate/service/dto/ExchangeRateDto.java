package com.example.exchange.rate.service.dto;

import com.example.exchange.rate.service.modal.ExchangeRate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeRateDto {
    private String from;
    private String to;
    private Double result;
    private String date;

    public static ExchangeRateDto from(ExchangeRate exchangeRate) {
        return new ExchangeRateDto(exchangeRate.getFrom(), exchangeRate.getTo(), exchangeRate.getResult(), exchangeRate.getDate());
    }

}
