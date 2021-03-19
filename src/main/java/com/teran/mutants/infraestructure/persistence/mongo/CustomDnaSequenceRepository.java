package com.teran.mutants.infraestructure.persistence.mongo;

import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Stats;
import reactor.core.publisher.Mono;

public interface CustomDnaSequenceRepository {

    Mono<Stats> getDnaSequenceStats();
    Mono<DnaSequence> guardarSecuencia(DnaSequence dnaSequence);
}
