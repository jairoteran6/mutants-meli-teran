package com.teran.mutants.infraestructure.shared.logger.log;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.io.StringWriter;

class LogFactory {


    private Gson gson=new Gson();

    public String createLog(String type, String message){
        return gson.toJson(new LogDto(factoryDetailLog(type,message)));
    }


    public String createLog(String type, Exception exception){
        return gson.toJson(new LogDto(
                factoryDetailLog(type,getMessageStack(exception)),
                factoryExceptionLog(exception)
        ));
    }

    private ExceptionLog factoryExceptionLog(Exception exception){
        return new ExceptionLog(
                getExceptionType(exception),
                getClassNameStack(exception),
                getMethodNameStack(exception),
                getLineNumberStack(exception),
                getMessageStack(exception)
        );
    }

    private ExceptionLog factoryExceptionLogWithStackTrace(Exception exception){
        return new ExceptionLog(
                getExceptionType(exception),
                getClassNameStack(exception),
                getMethodNameStack(exception),
                getLineNumberStack(exception),
                getMessageStack(exception),
                getStackTrace(exception)
        );
    }

    private String getClassNameLog(){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if(elements!=null && elements.length>=5){
            return elements[5].getClassName();
        }else{
            return null;
        }
    }

    private String getMethodNameLog(){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if(elements!=null && elements.length>=5){
            return elements[5].getMethodName();
        }else{
            return null;
        }
    }

    private DetailLog factoryDetailLog(String type, String message){
        return new DetailLog(
                type,
                getClassNameLog(),
                getMethodNameLog(),
                message
        );
    }

    private String getClassNameStack(Exception exception){
        StackTraceElement[] elements = exception.getStackTrace();
        if(elements!=null && elements.length>=2){
            return elements[1].getClassName();
        }else{
            return null;
        }
    }

    private String getMethodNameStack(Exception exception){
        StackTraceElement[] elements = exception.getStackTrace();
        if(elements!=null && elements.length>=2){
            return elements[1].getMethodName();
        }else{
            return null;
        }
    }

    private int getLineNumberStack(Exception exception){
        StackTraceElement[] elements = exception.getStackTrace();
        if(elements!=null && elements.length>=2){
            return elements[1].getLineNumber();
        }else{
            return 0;
        }
    }

    private String getMessageStack(Exception exception){
            return exception.getMessage();
    }

    private String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    private String getExceptionType(Exception exception){
        return exception.getClass().toString().contains("BusinessException")?"BusinessException":"TechnicalException";
    }

}
