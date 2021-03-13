package com.teran.mutants.domain.model;

import com.teran.mutants.domain.exception.ExceptionFactory;
import com.teran.mutants.domain.exception.Validate;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DnaSequence implements Serializable {

    private String[] dna;
    private HumanClasification humanClasification;

    public DnaSequence(){}

    private DnaSequence(String[] dna) {
        this.dna = dna;
    }

    private DnaSequence(String[] dna, HumanClasification humanClasification) {
        this.dna = dna;
        this.humanClasification = humanClasification;
    }

    public static Mono<DnaSequence> create(String[] dna, HumanClasification humanClasification){
        DnaSequence dnaSequence = new DnaSequence(dna,humanClasification);
        return dnaSequence.validate().then(Mono.just(dnaSequence));

    }


    public Mono<Void> validate(){
        return Validate.nullEntityValidate(dna,"dna");
    }

    public void validateCaractersInSequence(){
        String regex = "[^ATCG]";
        Pattern patron = Pattern.compile(regex);
        Mono.just(dna).filter(x -> Arrays.stream(x).anyMatch(b -> patron.matcher(b).find()))
                .switchIfEmpty(Mono.error(ExceptionFactory.VALUE_NOT_VALID.get(dna)))
                .then(Mono.empty());

    }

    public Mono<Void> validateToSave(){
        return Validate.nullEntityValidate(dna,"dna").
                switchIfEmpty(Validate.nullEntityValidate(humanClasification,"humanClasification"));

    }





}
