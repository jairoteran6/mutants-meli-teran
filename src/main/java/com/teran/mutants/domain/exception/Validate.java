package com.teran.mutants.domain.exception;

import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class Validate {

    //SOLO VALIDACIONES GENERICAS//
    //VALIDACIONES ESPECIFICAS EN EL MODELO O SERVICIO//

    private static String regexExcluding = "[^ATCG]";
    private static Pattern patronExcluding = Pattern.compile(regexExcluding);

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

    public static Mono<Void> validateCaractersInSequence(String[] dna, String field){
        return Mono.just(Arrays.stream(dna).anyMatch(a -> patronExcluding.matcher(a).find())).filter(b -> b==false)
                .switchIfEmpty(Mono.error(ExceptionFactory.VALUE_NOT_VALID.get(dna)))
                .then(Mono.empty());

    }

    public static Mono<Void> validateEstructureInSequence(String[] dna, String field){
        return Mono.just(Arrays.stream(dna)
                .allMatch(a -> dna.length == a.length()))
                .filter(b -> b==true)
                .switchIfEmpty(Mono.error(ExceptionFactory.VALUE_NOT_VALID.get(dna)))
                .then(Mono.empty());

    }


}
