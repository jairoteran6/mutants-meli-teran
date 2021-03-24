package com.teran.mutants.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class StatsTest {

    @Test
    @DisplayName("test get ratio when count_mutant_dna is zero and count_human_dna is zero")
    void testGetRatioCountMutantDnaZeroAndCountHumanDnaZero(){
        Stats stats=new Stats(0l,0l,0.0);

        Assertions.assertEquals(stats.getRatio(),0);

    }

    @Test
    @DisplayName("test get ratio when count_human_dna is zero")
    void testGetRatioCountHumanDnaZero(){
        Stats stats=new Stats(2l,0l,0.0);

        Assertions.assertEquals(stats.getRatio(),1);

    }

    @Test
    @DisplayName("test get ratio when count_mutant_dna and count_human_dna are different of zero")
    void testGetRatioCountMutantDnaAndCountHumanDnaDifferentOfZero(){
        Stats stats=new Stats(4l,2l,0.0);

        Assertions.assertEquals(stats.getRatio(),2);

    }
}
