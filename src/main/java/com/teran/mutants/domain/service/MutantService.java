package com.teran.mutants.domain.service;

import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.HumanClasification;
import com.teran.mutants.domain.service.dependency.MutantRepositoryI;
import reactor.core.publisher.Mono;

public class MutantService {

    private MutantRepositoryI mutantRepository;

    public MutantService( MutantRepositoryI mutantRepository) {
        this.mutantRepository=mutantRepository;
    }


    public Mono<DnaSequence> isMutant(String[] dna) {
            return DnaSequence.create(dna, HumanClasification.NORMAL)
                    .onErrorResume(error-> {
                        System.out.println("Error"+error);
                        return Mono.error(error);
                    });

    }
}
