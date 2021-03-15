package com.teran.mutants.domain.service;

import com.teran.mutants.domain.exception.ExceptionFactory;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.HumanClasification;
import com.teran.mutants.domain.service.dependency.MutantRepositoryI;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class MutantService {

    private MutantRepositoryI mutantRepository;

    public MutantService( MutantRepositoryI mutantRepository) {
        this.mutantRepository=mutantRepository;
    }


    public Mono<HumanClasification> isMutant(String[] dna) {
            return DnaSequence.create(dna, HumanClasification.NORMAL)
                    .flatMap(dnaSequence -> dnaSequence.verifyHumanClasification())
                    .onErrorResume(error-> {
                        System.out.println("Error"+error);
                        return Mono.error(error);
                    });

    }
}
