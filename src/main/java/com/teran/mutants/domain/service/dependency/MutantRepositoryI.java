package com.teran.mutants.domain.service.dependency;

import reactor.core.publisher.Mono;

public interface MutantRepositoryI {
    Mono<String> guardarSecuencia(String[] dna);
}
