package com.teran.mutants.application;

import com.teran.mutants.domain.exception.BusinessException;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Stats;
import com.teran.mutants.domain.model.HumanClasification;
import com.teran.mutants.domain.service.MutantService;
import com.teran.mutants.infraestructure.shared.dto.SequenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class MutantCtr {

    @Autowired
    MutantService mutanteService;

    @GetMapping("/stats")
    public Mono<Stats> getStats() {
        Stats stats = new Stats(1, 2, 0.2);

        return Mono.just(stats);
    }

    @PostMapping(path = "/mutant", consumes = "application/json")
    public Mono<HumanClasification> verificarMutante(@RequestBody SequenceDTO sequenceDTO) {
        return mutanteService.isMutant(sequenceDTO.getDna())
                .flatMap(humanClasification -> DnaSequence.create(sequenceDTO.getDna(), humanClasification))
                .map(dnaSequence -> mutanteService.guardarDnaSequence(dnaSequence))
                .flatMap(dnaSequence -> dnaSequence)
                .flatMap(dnaSequence -> {
                            return HumanClasification.MUTANT.equals(dnaSequence.getHumanClasification()) ?
                                    Mono.just(dnaSequence.getHumanClasification()) :
                                    Mono.error(new BusinessException("Humano", 403));
                        }
                )
                .onErrorResume(error -> {
                    System.out.println(error.getMessage());
                    return Mono.error(error);
                });
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Humano")
    @ExceptionHandler(BusinessException.class)
    public void businessException() {

    }


}
