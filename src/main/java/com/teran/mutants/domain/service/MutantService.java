package com.teran.mutants.domain.service;

import com.teran.mutants.domain.service.dependency.MutantRepositoryI;
import reactor.core.publisher.Mono;

public class MutantService {

    private MutantRepositoryI mutantRepository;

    public MutantService( MutantRepositoryI mutantRepository) {
        this.mutantRepository=mutantRepository;
    }


    public Mono<String> isMutant(String[] dna) {

        for(String cadena:dna){
            System.out.println(cadena.toString());
        }

        return mutantRepository.guardarSecuencia(dna);
    }
}
