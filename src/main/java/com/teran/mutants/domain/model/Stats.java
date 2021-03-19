package com.teran.mutants.domain.model;

import reactor.core.publisher.Mono;

public class Stats {

    private long count_mutant_dna;
    private long count_human_dna;
    private double ratio;

    public Stats() {
    }

    public Stats(long count_mutant_dna, long count_human_dna, double ratio) {
        this.count_mutant_dna = count_mutant_dna;
        this.count_human_dna = count_human_dna;
        this.ratio = ratio;
    }

    public long getCount_mutant_dna() {
        return count_mutant_dna;
    }

    public long getCount_human_dna() {
        return count_human_dna;
    }

    public double getRatio() {
        if (count_mutant_dna != 0) {
            if (count_human_dna == 0) {
                 ratio=1;
            } else {
                 ratio =(count_mutant_dna*1.0 / count_human_dna);
            }
        }
        return ratio;
    }
}
