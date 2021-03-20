package com.teran.mutants.domain.exception;

public class HumanException extends RuntimeException {


    public HumanException(String message) {
        super(message);

    }

    public HumanException(String message, Throwable causa) {
        super(message, causa);

    }

}


