package com.teran.mutants.infraestructure.persistence.mongo.model;


import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class DnaSequenceModel implements Serializable {


    private String dna;
    private String humanClasification;


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
