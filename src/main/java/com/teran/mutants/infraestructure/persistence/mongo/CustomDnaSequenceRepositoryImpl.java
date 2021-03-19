package com.teran.mutants.infraestructure.persistence.mongo;

import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Stats;
import com.teran.mutants.infraestructure.persistence.mongo.model.DnaSequenceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class CustomDnaSequenceRepositoryImpl implements CustomDnaSequenceRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    public CustomDnaSequenceRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<DnaSequence> guardarSecuencia(DnaSequence dnaSequence) {

        return Mono.from(mongoTemplate.save(new DnaSequenceModel(Arrays.stream(dnaSequence.getDna()).reduce("", String::concat), Arrays.toString(dnaSequence.getDna()), dnaSequence.getHumanClasification().toString())))
                .map(success -> dnaSequence)
                .onErrorResume(error -> {
                    System.out.println("Error guardando dnaSequence");
                    return Mono.error(new Exception(error));
                });
    }

    @Override
    public Mono<Stats> getDnaSequenceStats() {
        return Mono.just(new Stats(12l, 12, 12.0));
    }
}
