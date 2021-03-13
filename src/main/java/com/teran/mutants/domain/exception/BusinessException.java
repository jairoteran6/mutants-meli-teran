package com.teran.mutants.domain.exception;

public class BusinessException extends RuntimeException {

    private int statusCode;

    public BusinessException(String message, int statusCode) {
        super(message);
        this.statusCode=statusCode;
    }

    public BusinessException(String message, Throwable causa, int statusCode) {
        super(message, causa);
        this.statusCode=statusCode;
    }

    public int getStatusCode(){
        return this.statusCode;
    }

}


