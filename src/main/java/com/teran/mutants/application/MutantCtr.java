package com.teran.mutants.application;

import com.teran.mutants.domain.exception.BusinessException;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Estadistica;
import com.teran.mutants.domain.model.HumanClasification;
import com.teran.mutants.domain.service.MutantService;
import com.teran.mutants.infraestructure.shared.dto.SequenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MutantCtr {

    @Autowired
    MutantService mutanteService;

    @GetMapping("/stats")
    public Mono<Estadistica> getStats() {
        Estadistica estadistica = new Estadistica(1,2,0.2);

        return Mono.just(estadistica);
    }

    @PostMapping(path ="/mutant", consumes = "application/json")
    public  Mono<HumanClasification> verificarMutante(@RequestBody SequenceDTO sequenceDTO){
        return mutanteService.isMutant(sequenceDTO.getDna())
                .flatMap(humanClasification -> DnaSequence.create(sequenceDTO.getDna(),humanClasification))
                .map(dnaSequence->mutanteService.guardarDnaSequence(dnaSequence))
                .flatMap(dnaSequence -> dnaSequence)
                .flatMap(dnaSequence -> {
                            return HumanClasification.MUTANT.equals(dnaSequence.getHumanClasification())?
                                    Mono.just(dnaSequence.getHumanClasification()):
                                    Mono.error(new BusinessException("Humano",409));
                        }
                        )
                .onErrorResume(error-> {
                    System.out.println("Error");
                    return Mono.error(error);
                });
    }

}
