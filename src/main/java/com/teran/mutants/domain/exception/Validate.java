package com.teran.mutants.domain.exception;

import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.regex.Pattern;

public class Validate {

    //SOLO VALIDACIONES GENERICAS//
    //VALIDACIONES ESPECIFICAS EN EL MODELO O SERVICIO//

    public static Mono<Void> nullOrEmptyValidate(String value, String field) {
        return Mono.just(value).filter(it-> (value!=null && !value.isEmpty()))
                .switchIfEmpty(Mono.error(ExceptionFactory.NULL_OR_EMPTY.get(field)))
                .then(Mono.empty());
    }

    public static Mono<Void> nullEntityValidate(Object value, String field) {
        return Mono.just(value).filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(ExceptionFactory.NULL_ENTITY.get(field)))
                .then(Mono.empty());
    }

}
