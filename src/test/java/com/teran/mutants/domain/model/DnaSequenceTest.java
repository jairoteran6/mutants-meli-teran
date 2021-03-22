package com.teran.mutants.domain.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

@ExtendWith(SpringExtension.class)
class DnaSequenceTest {

    private DnaSequence dnaSequence = new DnaSequence();

    @Test
    @DisplayName("test build vertical sequence from horizontal sequence Ok")
    void testBuildVerticalDnaSequenceOk(){
        String[] sequenceHorizontal = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        String[] sequenceVertical = {"ACTACT","TATGCC","GGAACA","CTTACC","GGGGTT","ACTGAG"};


        Assertions.assertArrayEquals(dnaSequence.buildVerticalDnaSequence(sequenceHorizontal),sequenceVertical);
    }

    @Test
    @DisplayName("test validate Dna Sequence ok")
    void testValidateOk(){
    String[] sequenceOk = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Mono<DnaSequence> dnaSequence = DnaSequence.create(sequenceOk,Clasification.HUMAN);
        StepVerifier.create(dnaSequence.map(seq -> seq.validate()))
            .consumeNextWith(s->s.block())
            .verifyComplete();

}

    @Test
    @DisplayName("test validate Dna Sequence error caracteres")
    void testValidateErrorCaracters(){
        String[] sequenceOk = {"ATGCG2","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Mono<DnaSequence> dnaSequence = DnaSequence.create(sequenceOk,Clasification.HUMAN);
        StepVerifier.create(dnaSequence.map(seq -> seq.validate()))
                .verifyError();

    }

    @Test
    @DisplayName("test validate Dna Sequence error Estructure")
    void testValidateErrorEstructure(){
        String[] sequenceOk = {"CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Mono<DnaSequence> dnaSequence = DnaSequence.create(sequenceOk,Clasification.HUMAN);
        StepVerifier.create(dnaSequence.map(seq -> seq.validate()))
                .verifyError();

    }

    @Test
    @DisplayName("test verifyHumanClasification Mutant")
    void testVerifyHumanClasificationMutant(){
        String[] sequenceOk = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Mono<DnaSequence> dnaSequence = DnaSequence.create(sequenceOk,Clasification.HUMAN);
        StepVerifier.create(dnaSequence.map(seq -> seq.verifyHumanClasification()))
                .consumeNextWith(s->{
                    Assertions.assertEquals(s.block(),Clasification.MUTANT);
                            })
                .verifyComplete();

    }

    @Test
    @DisplayName("test verifyHumanClasification Human")
    void testVerifyHumanClasificationHuman(){
        String[] sequenceOk = {"ATGCGA","CAGTGC","TTATGT","AGCATG","CACCTA","TCACTG"};
        Mono<DnaSequence> dnaSequence = DnaSequence.create(sequenceOk,Clasification.HUMAN);
        StepVerifier.create(dnaSequence.map(seq -> seq.verifyHumanClasification()))
                .consumeNextWith(s->{
                    Assertions.assertEquals(s.block(),Clasification.HUMAN);
                })
                .verifyComplete();

    }


}
