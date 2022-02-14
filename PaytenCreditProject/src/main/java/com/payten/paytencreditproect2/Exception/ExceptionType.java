package com.payten.paytencreditproect2.Exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {

    ID_EXIST(1, "This id belongs to someone else, you cannot make credit application with this id."),
    ID_NOTEXIST(2, "Request should has user ID"),
    USER_NOTFOUND(3,"User not found at the database, please give user ID which registered in the database");

    private final Integer code;
    private final String message;
}
