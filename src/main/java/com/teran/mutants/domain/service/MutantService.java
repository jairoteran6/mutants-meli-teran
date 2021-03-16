package com.teran.mutants.domain.service;


import com.teran.mutants.domain.exception.Validate;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.HumanClasification;
import com.teran.mutants.domain.service.dependency.DnaSequenceRepositoryI;
import reactor.core.publisher.Mono;


public class MutantService {

    private DnaSequenceRepositoryI mutantRepository;

    public MutantService(DnaSequenceRepositoryI mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    public Mono<HumanClasification> isMutant(String[] dna) {
        return DnaSequence.create(dna, HumanClasification.NORMAL)
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
                            return mutantRepository.guardarSecuencia(dnaSequence);
                        }
                ))
                .onErrorResume(error-> {
                    //LOG.error((Exception) error);
                    System.out.println("Error");
                    return Mono.error(error);
                });
    }
}
