package com.teran.mutants.infraestructure.shared.dto;

import java.io.Serializable;

public class SequenceDTO implements Serializable {

    private String[] dna;

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}
