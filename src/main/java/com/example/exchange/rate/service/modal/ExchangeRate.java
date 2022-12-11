package com.example.exchange.rate.service.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeRate {
    private String from;
    private String to;
    private Double result;
    private String date;
}
