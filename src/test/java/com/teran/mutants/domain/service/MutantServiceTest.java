package com.teran.mutants.domain.service;

import com.teran.mutants.domain.exception.ExceptionFactory;
import com.teran.mutants.domain.model.Clasification;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Stats;
import com.teran.mutants.infraestructure.persistence.DnaSequenceReactiveRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class MutantServiceTest {

    DnaSequenceReactiveRepository dnaSequenceReactiveRepository;
    MutantService mutantService;

    @BeforeEach
    void InitTest() {
        dnaSequenceReactiveRepository = Mockito.mock(DnaSequenceReactiveRepository.class);
        mutantService = new MutantService(dnaSequenceReactiveRepository);
    }

    @Test
    @DisplayName("test isMutant -> Clasification Mutant")
    void testIsMutantClasificationMutant() {
        String[] sequenceMutant = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        StepVerifier.create(mutantService.isMutant(sequenceMutant))
                .consumeNextWith(
                        clasification -> Assertions.assertEquals(clasification, Clasification.MUTANT)
                ).verifyComplete();

    }

    @Test
    @DisplayName("test isMutant -> Clasification Human")
    void testIsMutantClasificationHuman() {
        String[] sequenceHuman = {"ATGCGA", "CAGTGC", "TTATGT", "AGCATG", "CACCTA", "TCACTG"};

        StepVerifier.create(mutantService.isMutant(sequenceHuman))
                .consumeNextWith(
                        clasification -> Assertions.assertEquals(clasification, Clasification.HUMAN)
                ).verifyComplete();

    }

    @Test
    @DisplayName("test isMutant -> Error estructure")
    void testIsMutantErrorEstructure() {
        String[] sequenceError = {"CAGTGC", "TTATGT", "AGCATG", "CACCTA", "TCACTG"};

        StepVerifier.create(mutantService.isMutant(sequenceError))
                .expectError().verify();

    }

    @Test
    @DisplayName("test isMutant -> Error caracters")
    void testIsMutantErrorCaracters() {
        String[] sequenceError = {"CAGT1C","CAGTGC", "TTATGT", "AGCATG", "CACCTA", "TCACTG"};

        StepVerifier.create(mutantService.isMutant(sequenceError))
                .expectError().verify();

    }

    @Test
    @DisplayName("test GuardarDnaSequenceOk")
    void testGuardarDnaSequenceOk() {
        String[] sequenceMutant = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        Mono<DnaSequence> dnaSequence = DnaSequence.create(sequenceMutant, Clasification.MUTANT);

        Mockito.when(dnaSequenceReactiveRepository.saveSequence(dnaSequence.block())).thenReturn(dnaSequence);

        StepVerifier.create(dnaSequence.map(sequence -> mutantService.guardarDnaSequence(sequence)))
                .consumeNextWith(sequence -> {
                            assertNotNull(sequence);
                        }

                ).verifyComplete();

    }

//TODO Ajustar esta prueba :(
    @DisplayName("test GuardarDnaSequence -> Error save sequence")
    void testGuardarDnaSequenceErrorSaveSequence() {
        String[] sequenceMutant = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        Mono<DnaSequence> dnaSequence = DnaSequence.create(sequenceMutant, Clasification.MUTANT);

        Mockito.when(dnaSequenceReactiveRepository.saveSequence(Mockito.any(DnaSequence.class))).thenReturn(Mono.error(new Exception()));

        StepVerifier.create(dnaSequence.map(sequence -> mutantService.guardarDnaSequence(sequence)))
                .consumeNextWith(respuesta -> {

                        }
                )
                .verifyComplete();

    }




}
