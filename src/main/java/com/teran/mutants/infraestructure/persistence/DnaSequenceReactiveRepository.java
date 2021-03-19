package com.teran.mutants.infraestructure.persistence;

import com.teran.mutants.infraestructure.persistence.mongo.CustomDnaSequenceRepository;
import com.teran.mutants.infraestructure.persistence.mongo.model.DnaSequenceModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DnaSequenceReactiveRepository extends ReactiveMongoRepository<DnaSequenceModel, String>, CustomDnaSequenceRepository {

}
