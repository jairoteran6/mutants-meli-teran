package com.teran.mutants.application;

import com.teran.mutants.domain.model.Clasification;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Stats;
import com.teran.mutants.domain.service.MutantService;
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

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = MutantCtr.class)
public class MutantCtrTest {

    @MockBean
    private MutantService mutantService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("test get Stats")
    void testGetStatsOk(){
        Stats stats = new Stats(25, 25,1.0);
        Mockito.when(mutantService.getStats()).thenReturn(Mono.just(stats));

        webTestClient.get()
                .uri("/stats")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Stats.class);

    }


    @DisplayName("test Verify Mutant -> Clasification Mutant true")
    void testVerifyMutantIsMutantTrue(){

        String[] sequenceDnaMutantTrue={"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Mono<DnaSequence> dnaSequence = DnaSequence.create(sequenceDnaMutantTrue,Clasification.MUTANT);

        Mockito.when(mutantService.isMutant(Mockito.any(String[].class)))
                .thenReturn(Mono.just(Clasification.MUTANT));

        Mockito.when(mutantService
                .guardarDnaSequence(dnaSequence.block()))
                .thenReturn(dnaSequence);

        webTestClient.post()
                .uri("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(sequenceDnaMutantTrue))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Clasification.class)
                .isEqualTo(Clasification.MUTANT);

        Mockito.verify(mutantService,Mockito.times(1)).guardarDnaSequence(dnaSequence.block());

    }


}
