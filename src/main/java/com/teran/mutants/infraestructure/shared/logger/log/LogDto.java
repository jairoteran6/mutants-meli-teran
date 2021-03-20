package com.teran.mutants.infraestructure.shared.logger.log;

import java.io.Serializable;
import java.util.HashMap;

class LogDto implements Serializable {

    private final DetailLog detailLog;

    private ExceptionLog exceptionLog;

    public LogDto(DetailLog detailLog) {
        this.detailLog = detailLog;
    }

    public LogDto(DetailLog detailLog, ExceptionLog exceptionLog) {
        this.detailLog = detailLog;
        this.exceptionLog = exceptionLog;
    }

}

class DetailLog implements Serializable {


    private final String type;
    private final String classNameLog;
    private final String methodNameLog;
    private final String message;


    public DetailLog(String type, String classNameLog, String methodNameLog, String message) {

        this.type = type;
        this.classNameLog = classNameLog;
        this.methodNameLog = methodNameLog;
        this.message = message;
    }

}

class ExceptionLog implements Serializable {

    private final String type;
    private final String classNameStack;
    private final String methodNameStack;
    private final int lineNumber;
    private final String messageStack;
    private String stackTrace;

    public ExceptionLog(String type, String classNameStack, String methodNameStack, int lineNumber, String messageStack, String stackTrace) {
        this.type = type;
        this.classNameStack = classNameStack;
        this.methodNameStack = methodNameStack;
        this.lineNumber = lineNumber;
        this.messageStack = messageStack;
        this.stackTrace = stackTrace;
    }

    public ExceptionLog(String type, String classNameStack, String methodNameStack, int lineNumber, String messageStack) {
        this.type = type;
        this.classNameStack = classNameStack;
        this.methodNameStack = methodNameStack;
        this.lineNumber = lineNumber;
        this.messageStack = messageStack;
    }


}