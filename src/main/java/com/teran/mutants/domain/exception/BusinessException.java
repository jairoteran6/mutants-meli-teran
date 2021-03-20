package com.teran.mutants.domain.exception;

public class BusinessException extends RuntimeException {


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable causa) {
        super(message, causa);
    }



}


