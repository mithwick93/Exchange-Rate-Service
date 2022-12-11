package com.example.exchange.rate.service.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Symbols {
    private List<String> currencySymbols;
    private String date;
}
