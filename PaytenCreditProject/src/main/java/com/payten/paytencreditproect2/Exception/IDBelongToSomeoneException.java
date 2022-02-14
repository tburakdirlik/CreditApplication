package com.payten.paytencreditproect2.Exception;

import lombok.Getter;

@Getter
public class IDBelongToSomeoneException extends RuntimeException{

    private final ExceptionType exceptionType;

    public IDBelongToSomeoneException(ExceptionType exceptionType) {

        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;

    }
}
