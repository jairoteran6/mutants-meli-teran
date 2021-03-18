package com.teran.mutants.domain.service.dependency;

import com.teran.mutants.domain.model.DnaSequence;
import reactor.core.publisher.Mono;


public interface DnaSequenceRepository {
    Mono<DnaSequence> guardarSecuencia(DnaSequence dnaSequence);
    Mono<DnaSequence> getDnaSequenceStats();
}
