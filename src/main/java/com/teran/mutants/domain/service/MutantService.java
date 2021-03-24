package com.teran.mutants.domain.service;


import com.teran.mutants.domain.exception.Validate;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Clasification;
import com.teran.mutants.domain.model.Stats;
import com.teran.mutants.infraestructure.persistence.DnaSequenceReactiveRepository;

import reactor.core.publisher.Mono;


public class MutantService extends Service{

    private final DnaSequenceReactiveRepository dnaSequenceReactiveRepository;

    public MutantService(DnaSequenceReactiveRepository dnaSequenceReactiveRepository) {
        this.dnaSequenceReactiveRepository = dnaSequenceReactiveRepository;
    }

    public Mono<Clasification> isMutant(String[] dna) {
        return DnaSequence.create(dna, Clasification.HUMAN)
                .flatMap(dnaSequence -> dnaSequence.verifyHumanClasification())
                .onErrorResume(error -> {
                    LOG.error((Exception) error);
                    return Mono.error(error);
                });

    }

    public Mono<DnaSequence> guardarDnaSequence(final DnaSequence dnaSequence) {
        return Validate.nullEntityValidate(dnaSequence, "DnaSequence")
                .switchIfEmpty(dnaSequence.validate())
                .then(Mono.defer(() -> {
                            LOG.audit("Se va a guardar la secuencia");
                            return dnaSequenceReactiveRepository.saveSequence(dnaSequence);
                        }
                ))
                .onErrorResume(error -> {
                    LOG.error((Exception) error);
                    return Mono.error(error);
                });
    }

    public Mono<Stats> getStats(){
        return dnaSequenceReactiveRepository.getDnaSequenceStats();
    }
}
