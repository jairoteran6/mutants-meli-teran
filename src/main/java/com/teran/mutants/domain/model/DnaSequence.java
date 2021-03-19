package com.teran.mutants.domain.model;

import com.teran.mutants.domain.exception.Validate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DnaSequence implements Serializable {

    private String[] dna;
    private Clasification clasification;
    private static String regexVerify = "(A{4}?|T{4}?|C{4}?|G{4}?)";

    public DnaSequence() {
    }

    private DnaSequence(final String[] dna, Clasification clasification) {
        this.dna = dna;
        this.clasification = clasification;
    }

    public static Mono<DnaSequence> create(final String[] dna, Clasification clasification) {
        DnaSequence dnaSequence = new DnaSequence(dna, clasification);
        return dnaSequence.validate().then(Mono.just(dnaSequence));
    }

    public Mono<Void> validate() {
        return Validate.nullEntityValidate(dna, "dna")
                .switchIfEmpty(Validate.validateCaractersInSequence(dna, "dna"))
                .then(Validate.validateEstructureInSequence(dna, "dna"))
                .then(Mono.empty());
    }

    public Mono<Clasification> verifyHumanClasification() {
        return Flux.merge(horizontalValidate(dna),
                verticalValidate(dna),
                obliqueValidate(dna))
                .reduce(Long::sum)
                .map(x -> x >= 2L ? Clasification.MUTANT : Clasification.HUMAN);

    }

    private Mono<Long> horizontalValidate(final String[] dna) {
        return countNumberOfMatchesInSequence(dna, regexVerify);
    }

    private Mono<Long> verticalValidate(final String[] dna) {

        return Mono.just(buildVerticalDnaSequence(dna))
                .map(dnaBuildVertical -> countNumberOfMatchesInSequence(dnaBuildVertical, regexVerify))
                .flatMap(x -> x);
    }

    private Flux<Long> obliqueValidate(final String[] dna) {
        return Flux.merge(Mono.just(buildObliqueDnaSequenceFromTopLeftToBottomRight(dna))
                        .map(dnaTopToBottom -> countNumberOfMatchesInSequence(dnaTopToBottom, regexVerify))
                        .flatMap(x -> x),
                Mono.just(buildObliqueDnaSequenceFromBottomLeftToTopRight(dna))
                        .map(dnaBottomToTop -> countNumberOfMatchesInSequence(dnaBottomToTop, regexVerify))
                        .flatMap(x -> x));
    }

    public Mono<Void> validateToSave() {
        return Validate.nullEntityValidate(dna, "dna").
                switchIfEmpty(Validate.nullEntityValidate(clasification, "humanClasification"));

    }

    private static Mono<Long> countNumberOfMatchesInSequence(final String[] dna, String regexValidate) {
        return Mono.just(Arrays.stream(dna).reduce(0L, (acumulador, a) ->
                acumulador + Pattern.compile(regexValidate).matcher(a).results().count(), Long::sum));
    }

    static String[] buildVerticalDnaSequence(final String[] dna) {

        String[] verticalDna = new String[dna.length];

        for (int row = 0; row < dna.length; row++) {
            StringBuilder verticalItem = new StringBuilder();
            for (String dnaItem : dna) {
                verticalItem.append(dnaItem.charAt(row));
            }
            verticalDna[row] = verticalItem.toString();
        }

        System.out.println(String.format("Vertical array %s", verticalDna.toString()));
        return verticalDna;
    }


    static String[] buildObliqueDnaSequenceFromTopLeftToBottomRight(final String[] dna) {

        List<String> obliqueDna = new ArrayList<>();

        for (int i = 0; i < dna.length / 2; i++) {

            StringBuilder sequenceUpper = new StringBuilder();
            StringBuilder sequenceLower = new StringBuilder();

            for (int j = 0; j < dna.length - i; j++) {
                sequenceUpper.append(dna[j].charAt(j + i));

                if (i != 0) {
                    sequenceLower.append(dna[i + j].charAt(j));
                }
            }

            if (sequenceUpper.length() > 0) {
                obliqueDna.add(sequenceUpper.toString());
            }

            if (sequenceLower.length() > 0) {
                obliqueDna.add(sequenceLower.toString());
            }
        }

        System.out.println(String.format("Oblique array build from top to bottom %s", obliqueDna.toString()));

        return obliqueDna.toArray(String[]::new);
    }

    static String[] buildObliqueDnaSequenceFromBottomLeftToTopRight(final String[] dna) {

        List<String> obliqueDna = new ArrayList<>();

        for (int i = 0; i < dna.length / 2; i++) {

            StringBuilder sequenceUpper = new StringBuilder();
            StringBuilder sequenceLower = new StringBuilder();

            for (int j = dna.length - 1; j >= i; j--) {
                sequenceUpper.append(dna[j].charAt(i + dna.length - 1 - j));

                if (i != 0) {
                    sequenceLower.append(dna[j - i].charAt(dna.length - 1 - j));
                }
            }

            if (sequenceUpper.length() > 0) {
                obliqueDna.add(sequenceUpper.toString());
            }

            if (sequenceLower.length() > 0) {
                obliqueDna.add(sequenceLower.toString());
            }
        }

        System.out.println(String.format("Oblique array build from bottom to top %s", obliqueDna.toString()));

        return obliqueDna.toArray(String[]::new);
    }

    public String[] getDna() {
        return dna;
    }

    public Clasification getHumanClasification() {
        return clasification;
    }
}
