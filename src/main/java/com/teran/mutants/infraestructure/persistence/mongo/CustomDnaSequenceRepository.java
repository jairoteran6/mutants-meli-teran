package com.teran.mutants.infraestructure.persistence.mongo;

import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Estadistica;
import reactor.core.publisher.Mono;

public interface CustomDnaSequenceRepository {

    Mono<Estadistica> getDnaSequenceStats();
    Mono<DnaSequence> guardarSecuencia(DnaSequence dnaSequence);
}
