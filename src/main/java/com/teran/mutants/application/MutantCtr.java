package com.teran.mutants.application;

import com.teran.mutants.domain.model.Estadistica;
import com.teran.mutants.infraestructura.shared.EstadisticaDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MutantCtr {

    @GetMapping("/stats")
    private Mono<Estadistica> getStats() {
        Estadistica estadistica = new Estadistica(1,2,0.2);
        return Mono.just(estadistica);
    }

}
