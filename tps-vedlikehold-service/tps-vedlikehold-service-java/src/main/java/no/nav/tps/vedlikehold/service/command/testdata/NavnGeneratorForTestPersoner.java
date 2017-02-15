package no.nav.tps.vedlikehold.service.command.testdata;

import no.nav.tps.vedlikehold.domain.service.tps.testdata.Kjonn;
import no.nav.tps.vedlikehold.domain.service.tps.testdata.TestDataPerson;

import java.util.concurrent.ThreadLocalRandom;

//TODO kanskje gjøre om til Bean slik at man kan Autowire.
public class NavnGeneratorForTestPersoner {

    // Fornavn Gutter
    static final String[] gutteNavn = {"TestLars, TestMartin, TestPeter"};

    // Fornavn Jenter
    static final String[] jenteNavn = {"TestList, TestTine, TestTiril"};

    // Mellomnavn
    static final String[] mellomnavn = {"Mellom"};

    // Etternavn
    static final String[] etternavn = {"Nordmann, Thomassen, Kaiassen"};

    public static void setTilfeldigNavnForTestPerson(TestDataPerson testDataPerson){
        //TODO Kan bare avgjøre kjønn basert på FNR.
        testDataPerson.setFornavn(testDataPerson.getKjonn().equals(Kjonn.MANN) ? genererTilfeldigGuttefornavn() : genererTilfeldigJentefornavn());
        testDataPerson.setEtternavn(genererTilfeldigEtternavn());
    }

    private static String genererTilfeldigJentefornavn(){
        return jenteNavn[ThreadLocalRandom.current().nextInt(0,jenteNavn.length)];
    }

    private static String genererTilfeldigGuttefornavn(){
        return gutteNavn[ThreadLocalRandom.current().nextInt(0,gutteNavn.length )];
    }

    private static String genererTilfeldigEtternavn(){
        return etternavn[ThreadLocalRandom.current().nextInt(0,etternavn.length)];
    }

    private static String genererTilfeldigMellomnavn(){
        return mellomnavn[ThreadLocalRandom.current().nextInt(0,mellomnavn.length)];
    }
}
