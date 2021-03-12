package com.teran.mutants.domain.service;

import com.teran.mutants.domain.service.dependency.MutantServiceI;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MutantService implements MutantServiceI {

    @Override
    public Mono<String> identificarMutante(String[] dna) {

        for(String cadena:dna){
            System.out.println(cadena);
        }

        return Mono.just("lo logramos");
    }
}
