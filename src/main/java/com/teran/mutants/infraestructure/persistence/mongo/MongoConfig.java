package com.teran.mutants.infraestructure.persistence.mongo;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.teran.mutants.infraestructure.persistence.mongo.model.DnaSequenceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.convert.MongoConverter;

@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Autowired
    private MongoClient mongoClient;


    @Bean
    MongoCollection<DnaSequenceModel> getMongoCollectionDnaSequence() {
        return mongoClient.getDatabase("mutant").getCollection("dnasecuence", DnaSequenceModel.class);
    }
}
