package com.teran.mutants.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = HealthCtr.class)
public class HealthCtrTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("test Health")
    void testHealth() throws Exception {
        String response = "{\n" +
                "  \"state\" : \"Ok\"\n" +
                "  \"app\" : \"mutant\"\n" +
                "}";

        webTestClient.get()
                .uri("/")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(response);

    }

}
