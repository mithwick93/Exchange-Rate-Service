package com.example.exchange.rate.service.dto;

import com.example.exchange.rate.service.modal.LatestRates;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LatestRatesDto {
    private String base;
    private String date;
    private Map<String, BigDecimal> exchangeRates;

    public static LatestRatesDto from(LatestRates latestRates) {
        return new LatestRatesDto(latestRates.getBase(), latestRates.getDate(), latestRates.getExchangeRates());
    }

}
