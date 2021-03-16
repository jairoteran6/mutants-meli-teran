package com.teran.mutants.infraestructure.persistence;

import com.mongodb.reactivestreams.client.MongoCollection;
import com.teran.mutants.domain.model.DnaSequence;
import com.teran.mutants.domain.model.HumanClasification;
import com.teran.mutants.domain.service.dependency.DnaSequenceRepositoryI;
import com.teran.mutants.infraestructure.persistence.mongo.model.DnaSequenceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.mongodb.client.model.Filters.eq;

@Service
public class DnaSequenceRepository implements DnaSequenceRepositoryI {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    MongoCollection<DnaSequenceModel> dnaSequenceDao;



    @Override
    public Mono<DnaSequence> guardarSecuencia(DnaSequence dnaSequence) {

        return Mono.just(dnaSequenceDao.insertOne(new DnaSequenceModel()))
                .map(success-> dnaSequence)
                .onErrorResume(error -> {
                    System.out.println("Error guardando dnaSequence");
                    return Mono.error(new Exception(error));
                });
    }


    @Override
    public Mono<DnaSequence> findDnaSequence(DnaSequence dnaSequence){
        return  Mono.from(dnaSequenceDao
                .find(eq("dna", "ATGDATT"))
                .first())
                .map(sequence -> mapper.map(sequence, DnaSequenceModel.class))
                .flatMap(secuencia ->  DnaSequence.create(new String[]{secuencia.getDna()}, HumanClasification.valueOf(secuencia.getHumanClasification())))
                .onErrorResume(error -> {
                    return Mono.error(new Exception(error));
                });
    }


}
