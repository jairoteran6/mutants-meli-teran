package com.teran.mutants.domain.exception;

public enum ExceptionFactory {

    NULL_OR_EMPTY("El campo <f0> no puede estar vacio",400),
    NULL_ENTITY("La entidad <f0> no puede ser nula",400),
    ERROR_SAVING("Error al momento de guardar la entidad <f0>",400),
    ENTITY_NOT_FOUND("Entidad <f0> no encontrada",404),
    VALUE_NOT_VALID("El campo <f0> no es valido",400);


    private String message;
    private int statusCode;
    ExceptionFactory(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    public BusinessException get(Throwable throwable, String... fields){
        replaceMessage(fields);
        return new BusinessException(this.message,throwable,this.statusCode);
    }
    public BusinessException get(String... fields){
        replaceMessage(fields);
        return new BusinessException(this.message, this.statusCode);
    }
    private void replaceMessage(String... fields){
        for (int i=0;i<= fields.length-1;i++){
            this.message = this.message.replaceAll("<f"+i+">",fields[i]);
        }
    }

}