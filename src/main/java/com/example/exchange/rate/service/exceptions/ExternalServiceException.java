package com.example.exchange.rate.service.exceptions;

import lombok.Getter;

@Getter
public class ExternalServiceException extends RuntimeException {
    private final String service;

    public ExternalServiceException(String service, String message) {
        super(message);
        this.service = service;
    }
}
