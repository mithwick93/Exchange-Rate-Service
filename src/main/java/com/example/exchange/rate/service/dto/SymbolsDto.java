package com.example.exchange.rate.service.dto;

import com.example.exchange.rate.service.modal.Symbols;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SymbolsDto {
    private List<String> symbols;
    private String date;

    public static SymbolsDto from(Symbols currencies) {
        return new SymbolsDto(currencies.getCurrencySymbols(), currencies.getDate());
    }
}
