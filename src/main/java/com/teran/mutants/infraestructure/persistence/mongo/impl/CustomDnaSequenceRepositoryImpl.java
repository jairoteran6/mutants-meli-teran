package com.teran.mutants.infraestructure.persistence.mongo.impl;

import com.teran.mutants.domain.model.Clasification;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.Stats;
import com.teran.mutants.infraestructure.persistence.mongo.CustomDnaSequenceRepository;
import com.teran.mutants.infraestructure.persistence.mongo.model.DnaSequenceModel;
import com.teran.mutants.infraestructure.persistence.mongo.model.StatsModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
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
    public Mono<DnaSequence> saveSequence(DnaSequence dnaSequence) {

        return Mono.from(mongoTemplate.save(new DnaSequenceModel(Arrays.stream(dnaSequence.getDna()).reduce("", String::concat), Arrays.toString(dnaSequence.getDna()), dnaSequence.getHumanClasification().toString())))
                .map(success -> dnaSequence)
                .onErrorResume(error -> {
                    System.out.println("Error guardando dnaSequence");
                    return Mono.error(new Exception(error));
                });
    }

    @Override
    public Mono<Stats> getDnaSequenceStats() {

        GroupOperation groupOperation = Aggregation.group().sum(ConditionalOperators
                .when(ComparisonOperators.valueOf("humanClasification").equalToValue(Clasification.MUTANT)).then(1).otherwise(0)).as("count_mutant_dna")
                .sum(ConditionalOperators
                        .when(ComparisonOperators.valueOf("humanClasification").equalToValue(Clasification.HUMAN)).then(1).otherwise(0)).as("count_human_dna");


        Aggregation aggregation = Aggregation.newAggregation(groupOperation);

        return Mono.from(mongoTemplate.aggregate(aggregation, "dnasequence", StatsModel.class))
                .map(statsModel -> mapper.map(statsModel, Stats.class))
                .onErrorResume(error -> {
                    System.out.println(new Exception(error));
                    return Mono.error(new Exception(error));
                });


    }

}
