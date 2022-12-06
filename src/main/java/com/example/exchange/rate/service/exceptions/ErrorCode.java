package com.example.exchange.rate.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INCORRECT_REQUEST_BODY(1000, Constants.INCORRECT_REQUEST_BODY_MSG),
    ERROR_LOADING_ECB_DATA(1002, Constants.ERROR_LOADING_ECB_DATA_MSG),
    INTERNAL_ERROR_SERVER(1004, Constants.INTERNAL_ERROR_SERVER),
    NO_SUCH_CURRENCY(1005, Constants.NO_SUCH_CURRENCY_MSG);
    private final int code;
    private final String msg;

    public static class Constants {
        public final static String INCORRECT_REQUEST_BODY_MSG = "Invalid JSON request body";
        public final static String ERROR_LOADING_ECB_DATA_MSG = "Problem with connection to ECB";
        public final static String INTERNAL_ERROR_SERVER = "Problem with the server. Please contact an administrator";
        public final static String NO_SUCH_CURRENCY_MSG = "Currency code is invalid";
    }
}
