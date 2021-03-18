package com.teran.mutants.infraestructure.persistence.mongo.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(value="dnasequence")
public class DnaSequenceModel {

    @Id
    private String id;
    private String dna;
    private String humanClasification;

    public DnaSequenceModel(String id, String dna, String humanClasification) {
        this.id = id;
        this.dna = dna;
        this.humanClasification = humanClasification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDna() {
        return dna;
    }

    public void setDna(String dna) {
        this.dna = dna;
    }

    public String getHumanClasification() {
        return humanClasification;
    }

    public void setHumanClasification(String humanClasification) {
        this.humanClasification = humanClasification;
    }
}
