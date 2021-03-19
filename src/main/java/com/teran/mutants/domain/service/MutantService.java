package com.teran.mutants.domain.service;


import com.teran.mutants.domain.exception.Validate;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Clasification;
import com.teran.mutants.domain.model.Stats;
import com.teran.mutants.infraestructure.persistence.DnaSequenceReactiveRepository;
import reactor.core.publisher.Mono;


public class MutantService {

    //private DnaSequenceReactiveRepository mutantRepository;
    private final DnaSequenceReactiveRepository mutantRepository;

    public MutantService(DnaSequenceReactiveRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    public Mono<Clasification> isMutant(String[] dna) {
        return DnaSequence.create(dna, Clasification.HUMAN)
                .flatMap(dnaSequence -> dnaSequence.verifyHumanClasification())
                .onErrorResume(error -> {
                    System.out.println("Error" + error);
                    return Mono.error(error);
                });

    }

    public Mono<DnaSequence> guardarDnaSequence(final DnaSequence dnaSequence) {
        return Validate.nullEntityValidate(dnaSequence, "DnaSequence")
                .switchIfEmpty(dnaSequence.validate())
                .then(Mono.defer(() -> {
                            //log.auditWithHttp("Se va a actualizar el cliente");
                            System.out.println("Se va a guardar la secuencia");
                            return mutantRepository.saveSequence(dnaSequence);
                        }
                ))
                .onErrorResume(error -> {
                    //LOG.error((Exception) error);
                    System.out.println("Error");
                    return Mono.error(error);
                });
    }

    public Mono<Stats> getStats(){
        return mutantRepository.getDnaSequenceStats();
    }
}
