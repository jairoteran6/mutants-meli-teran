package com.teran.mutants.application;

import com.teran.mutants.domain.exception.ExceptionFactory;
import com.teran.mutants.domain.model.Clasification;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Stats;
import com.teran.mutants.domain.service.MutantService;
import com.teran.mutants.infraestructure.shared.dto.SequenceDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = MutantCtr.class)
class MutantCtrTest {

    @MockBean
    private MutantService mutantService;

    @Autowired
    private WebTestClient webTestClient;


    @Test
    @DisplayName("test get Stats")
    void testGetStatsOk(){

        webTestClient.get()
                .uri("/stats")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Stats.class);

    }


    @Test
    @DisplayName("test Verify Mutant -> Clasification Mutant true")
    void testVerifyMutantIsMutantTrue(){

        String[] sequenceDnaMutantTrue={"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};

        Mono<DnaSequence> dnaSequenceMutant = DnaSequence.create(sequenceDnaMutantTrue,Clasification.MUTANT);

        SequenceDTO sequenceDTO = new SequenceDTO();
        sequenceDTO.setDna(sequenceDnaMutantTrue);

        Mockito.when(mutantService.isMutant(sequenceDnaMutantTrue)).thenReturn(Mono.just(Clasification.MUTANT));
        Mockito.when(mutantService.guardarDnaSequence(Mockito.any(DnaSequence.class))).thenReturn(dnaSequenceMutant);


        webTestClient
                .post()
                .uri("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(sequenceDTO)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Clasification.class)
                .isEqualTo(Clasification.MUTANT);

    }

    @Test
    @DisplayName("test Verify Mutant -> Clasification Human true, response error status 403")
    void testVerifyMutantIsHumanTrue(){

        String[] sequenceDnaHumanTrue={"ATGCGA","CAGTGC","TTATGT","AGCATG","CACCTA","TCACTG"};

        Mono<DnaSequence> dnaSequenceHuman = DnaSequence.create(sequenceDnaHumanTrue,Clasification.HUMAN);

        SequenceDTO sequenceDTO = new SequenceDTO();
        sequenceDTO.setDna(sequenceDnaHumanTrue);

        Mockito.when(mutantService.isMutant(sequenceDnaHumanTrue)).thenReturn(Mono.just(Clasification.HUMAN));
        Mockito.when(mutantService.guardarDnaSequence(Mockito.any(DnaSequence.class))).thenReturn(dnaSequenceHuman);


        webTestClient
                .post()
                .uri("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(sequenceDTO)
                .exchange()
                .expectStatus()
                .isForbidden();



    }

    @Test
    @DisplayName("test Verify Mutant -> Error Sequence, response error status 400")
    void testVerifyMutantBadRequest(){

        String[] sequenceDnaError={"CAGTGC","TTATGT","AGCATG","CACCTA","TCACTG"};

        Mono<DnaSequence> dnaSequenceError = DnaSequence.create(sequenceDnaError,Clasification.HUMAN);

        SequenceDTO sequenceDTO = new SequenceDTO();
        sequenceDTO.setDna(sequenceDnaError);

        Mockito.when(mutantService.isMutant(sequenceDnaError)).thenReturn(Mono.error(ExceptionFactory.VALUE_NOT_VALID.get(Arrays.toString(sequenceDnaError))));
        Mockito.when(mutantService.guardarDnaSequence(Mockito.any(DnaSequence.class))).thenReturn(dnaSequenceError);


        webTestClient
                .post()
                .uri("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(sequenceDTO)
                .exchange()
                .expectStatus()
                .isBadRequest();



    }


}
