package com.teran.mutants.application;

import com.teran.mutants.domain.model.Estadistica;
import com.teran.mutants.domain.service.dependency.MutantServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MutantCtr {

    @Autowired
    MutantServiceI mutanteService;

    @GetMapping("/stats")
    private Mono<Estadistica> getStats() {
        Estadistica estadistica = new Estadistica(1,2,0.2);

        return Mono.just(estadistica);
    }

    @GetMapping("/mutant")
    private  Mono<String> verificarMutante(){
        return mutanteService.isMutant(new String[]{"123","112"});
    }

}
