package com.teran.mutants.domain.exception;

public enum ExceptionFactory {

    NULL_OR_EMPTY("El campo <f0> no puede estar vacio"),
    NULL_ENTITY("La entidad <f0> no puede ser nula"),
    ERROR_SAVING("Error al momento de guardar la entidad <f0>"),
    VALUE_NOT_VALID("El campo <f0> no es valido"),
    HUMAN_SEQUENCE("La secuencia <f0> es humana");


    private String message;

    ExceptionFactory(String message) {
        this.message = message;
    }
    public BusinessException get(Throwable throwable, String... fields){
        replaceMessage(fields);
        return new BusinessException(this.message,throwable);
    }
    public BusinessException get(String... fields){
        replaceMessage(fields);
        return new BusinessException(this.message);
    }

    public HumanException getHumanException(String secuencia){
        replaceMessage(secuencia);
        return new HumanException(this.message);
    }

    private void replaceMessage(String... fields){
        for (int i=0;i<= fields.length-1;i++){
            this.message = this.message.replaceAll("<f"+i+">",fields[i]);
        }
    }

}