package com.payten.paytencreditproect2.Exception;

import lombok.Getter;

@Getter
public class RequestHasNotIDException extends RuntimeException {

    private final ExceptionType exceptionType;

    public RequestHasNotIDException(ExceptionType exceptionType) {

        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;

    }
}
