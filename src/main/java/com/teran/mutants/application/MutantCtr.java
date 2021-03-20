package com.teran.mutants.application;

import com.teran.mutants.domain.exception.BusinessException;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Stats;
import com.teran.mutants.domain.model.Clasification;
import com.teran.mutants.domain.service.MutantService;
import com.teran.mutants.infraestructure.shared.dto.SequenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class MutantCtr extends Ctr{

    @Autowired
    MutantService mutanteService;

    @GetMapping("/stats")
    public Mono<Stats> getStats() {

        return mutanteService.getStats();
    }

    @PostMapping(path = "/mutant", consumes = "application/json")
    public Mono<Clasification> verificarMutante(@RequestBody SequenceDTO sequenceDTO) {
        return mutanteService.isMutant(sequenceDTO.getDna())
                .flatMap(clasification -> DnaSequence.create(sequenceDTO.getDna(), clasification))
                .map(dnaSequence -> mutanteService.guardarDnaSequence(dnaSequence))
                .flatMap(dnaSequence -> dnaSequence)
                .flatMap(dnaSequence -> {
                            return Clasification.MUTANT.equals(dnaSequence.getHumanClasification()) ?
                                    Mono.just(dnaSequence.getHumanClasification()) :
                                    Mono.error(new BusinessException(Clasification.HUMAN.toString(), 403));
                        }
                )
                .onErrorResume(error -> {
                    LOG.error((Exception) error);
                    return Mono.error(error);
                });
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Human")
    @ExceptionHandler(BusinessException.class)
    public void businessException() {

    }


}
