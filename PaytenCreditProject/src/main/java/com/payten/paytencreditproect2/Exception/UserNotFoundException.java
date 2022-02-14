package com.payten.paytencreditproect2.Exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    private final ExceptionType exceptionType;

    public UserNotFoundException(ExceptionType exceptionType) {

        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;

    }
}
