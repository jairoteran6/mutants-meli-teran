package com.teran.mutants.application;

import com.teran.mutants.domain.exception.BusinessException;
import com.teran.mutants.domain.exception.ExceptionFactory;
import com.teran.mutants.domain.exception.HumanException;
import com.teran.mutants.domain.model.Clasification;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Stats;
import com.teran.mutants.domain.service.MutantService;
import com.teran.mutants.infraestructure.shared.dto.SequenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * Controlador o servicio de aplicacion, debe contener las interfaces
 */
@RestController
public class MutantCtr extends Ctr {

    @Autowired
    MutantService mutanteService;

    @GetMapping("/stats")
    public Mono<Stats> getStats() {

        return mutanteService.getStats();
    }

    /**
     * @param sequenceDTO
     * @return
     */

    @PostMapping(path = "/mutant", consumes = "application/json")
    public Mono<Clasification> verifyMutant(@RequestBody SequenceDTO sequenceDTO) {
        return mutanteService.isMutant(sequenceDTO.getDna())
                .flatMap(clasification -> DnaSequence.create(sequenceDTO.getDna(), clasification))
                .flatMap(dnaSequence -> mutanteService.guardarDnaSequence(dnaSequence))
                .flatMap(dnaSequence ->
                        Clasification.MUTANT.equals(dnaSequence.getHumanClasification()) ?
                                Mono.just(dnaSequence.getHumanClasification()) :
                                Mono.error(ExceptionFactory.HUMAN_SEQUENCE.getHumanException(
                                        Arrays.toString(dnaSequence.getDna())))

                )
                .onErrorResume(error -> {
                    LOG.error((Exception) error);
                    return Mono.error(error);
                });
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(HumanException.class)
    public void humanException() {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public void businessException() {
    }


}
