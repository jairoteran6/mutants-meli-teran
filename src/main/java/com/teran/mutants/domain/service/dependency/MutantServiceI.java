package com.teran.mutants.domain.service.dependency;

import reactor.core.publisher.Mono;

public interface MutantServiceI {
    Mono<String> isMutant(String[] dna);
}
