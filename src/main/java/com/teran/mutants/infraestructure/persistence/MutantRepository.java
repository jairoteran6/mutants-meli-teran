package com.teran.mutants.infraestructure.persistence;

import com.teran.mutants.domain.service.dependency.MutantRepositoryI;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Scope("singleton")
public class MutantRepository implements MutantRepositoryI {

    @Override
    public Mono<String> guardarSecuencia(String[] dna) {
        return Mono.just("Lo logramos!!");
    }
}
