package com.teran.mutants.application;

import com.teran.mutants.domain.model.Stats;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HealthCtr {
    @GetMapping("/")
    public Mono<String> health() {

        return Mono.just("{\n" +
                "  \"state\" : \"Ok\"\n" +
                "  \"app\" : \"mutant\"\n" +
                "}");
    }
}
