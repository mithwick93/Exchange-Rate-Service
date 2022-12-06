package com.example.exchange.rate.service.exceptions;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode code;
    private final String details;

    public CustomException(ErrorCode code, String details) {
        super(details);
        this.code = code;
        this.details = details;
    }
}
