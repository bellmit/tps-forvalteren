package no.nav.tps.forvalteren.service.command.testdata;

import no.nav.tps.forvalteren.domain.rs.RsPersonKriterier;
import no.nav.tps.forvalteren.service.command.testdata.utils.BiasedRandom;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class FiktiveIdenterGenerator {

    private static final int CATEGORY1_NUMBER_RANGE_START = 0;
    private static final int CATEGORY1_NUMBER_RANGE_END = 499;
    private static final int CATEGORY1_TIME_PERIOD_START = 1900;
    private static final int CATEGORY1_TIME_PERIOD_END = 1999;

    private static final int CATEGORY2_NUMBER_RANGE_START = 500;
    private static final int CATEGORY2_NUMBER_RANGE_END = 749;
    private static final int CATEGORY2_TIME_PERIOD_START = 1854;
    private static final int CATEGORY2_TIME_PERIOD_END = 1899;

    private static final int CATEGORY_3_NUMBER_RANGE_START = 500;
    private static final int CATEGORY_3_NUMBER_RANGE_END = 999;
    private static final int CATEGORY_3_TIME_PERIOD_START = 2000;
    private static final int CATEGORY_3_TIME_PERIOD_END = 2039;

    private static final int CATEGORY4_NUMBER_RANGE_START = 900;
    private static final int CATEGORY4_NUMBER_RANGE_END = 999;
    private static final int CATEGORY4_TIME_PERIOD_START = 1949;
    private static final int CATEGORY4_TIME_PERIOD_END = 1999;

    private static final int[] KONTROLL_SIFFER_C1 = {3, 7, 6, 1, 8, 9, 4, 5, 2};
    private static final int[] KONTROLL_SIFFER_C2 = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};

    public List<String> genererFiktiveIdenter(List<RsPersonKriterier> personKriterierListe){
        ArrayList<String> fiktiveIdenter = new ArrayList<>();
        for(RsPersonKriterier kriterier : personKriterierListe){
            fiktiveIdenter.addAll(this.genererFiktiveIdenter(kriterier));
        }
        return fiktiveIdenter;
    }

    private List<String> genererFiktiveIdenter(RsPersonKriterier personKriterie) {
        switch (personKriterie.getIdenttype()) {
            case "DNR":
                return genererNyttDnummer(personKriterie);
            case "BNR":
                return genererNyttBNummer(personKriterie);
            default:
                return genererNyttFnr(personKriterie);
        }
    }


    //TODO Lag en random dato i et intervall! Og deretter bare bruk hele klassen.

    private List<String> genererNyttFnr(RsPersonKriterier kriterie) {
        String fodselsdatodato = localDateToDDmmYYStringFormat(kriterie.getFodtEtter());
        return genererIdenter(kriterie, fodselsdatodato);
    }

    private List<String> genererNyttDnummer(RsPersonKriterier kriterie) {
        String fodselsdato = localDateToDDmmYYStringFormat(kriterie.getFodtEtter());

        int forsteSiffer = Character.getNumericValue(fodselsdato.charAt(0)) + 4;
        String dfdato = Integer.toString(forsteSiffer) + fodselsdato.substring(1);

        return genererIdenter(kriterie, dfdato);
    }

    private List<String> genererNyttBNummer(RsPersonKriterier kriterie) {
        String fodselsdato = localDateToDDmmYYStringFormat(kriterie.getFodtEtter());

        int maanedSiffer = Character.getNumericValue(fodselsdato.charAt(2)) + 2;
        String bNummerDato = fodselsdato.substring(0,2) + Integer.toString(maanedSiffer) + fodselsdato.substring(3);

        return genererIdenter(kriterie, bNummerDato);
    }

    private String localDateToDDmmYYStringFormat(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        return date.format(formatter);
    }

    private List<String> genererIdenter(RsPersonKriterier kriterie, String fodselsdato) {
        StringBuilder identitetBuilder;
        HashSet<String> identSet = new HashSet<>();
        while(identSet.size() != kriterie.getAntall()){
            identitetBuilder = new StringBuilder();
            List<Integer> rangeList = hentKategoriIntervallForDato(kriterie.getFodtEtter());
            identitetBuilder.append(fodselsdato).append(genererIndividnummer(rangeList.get(0), rangeList.get(1), kriterie.getKjonn()));
            int forsteKontrollSiffer = hentForsteKontrollSiffer(identitetBuilder.toString());
            if(forsteKontrollSiffer == 10){
                // Hvis kontrollsiffer er 10, så må fodselsnummeret forkastes, og man prøver å lage et nytt.
                continue;
            }
            identitetBuilder.append(forsteKontrollSiffer);
            int andreKontrollSiffer = getAndreKontrollSiffer(identitetBuilder.toString());
            if(andreKontrollSiffer == 10){
                continue;
            }
            identitetBuilder.append(andreKontrollSiffer);
            String fnr = identitetBuilder.toString();
            identSet.add(fnr);
        }
        return new ArrayList<>(identSet);
    }

    private List<Integer> hentKategoriIntervallForDato(LocalDate date) {
        List<Integer> rangeList = new ArrayList<>();
        if (isInYearRange(date, CATEGORY1_TIME_PERIOD_START, CATEGORY1_TIME_PERIOD_END)) {
            rangeList.addAll(Arrays.asList(CATEGORY1_NUMBER_RANGE_START, CATEGORY1_NUMBER_RANGE_END));
        } else if (isInYearRange(date, CATEGORY2_TIME_PERIOD_START, CATEGORY2_TIME_PERIOD_END)) {
            rangeList.addAll(Arrays.asList(CATEGORY2_NUMBER_RANGE_START, CATEGORY2_NUMBER_RANGE_END));
        } else if (isInYearRange(date, CATEGORY_3_TIME_PERIOD_START, CATEGORY_3_TIME_PERIOD_END)) {
            rangeList.addAll(Arrays.asList(CATEGORY_3_NUMBER_RANGE_START, CATEGORY_3_NUMBER_RANGE_END));
        } else if (isInYearRange(date, CATEGORY4_TIME_PERIOD_START, CATEGORY4_TIME_PERIOD_END)) {
            rangeList.addAll(Arrays.asList(CATEGORY4_NUMBER_RANGE_START, CATEGORY4_NUMBER_RANGE_END));
        }
        return rangeList;
    }

    private String genererIndividnummer(int rangeStart, int rangeSlutt, Character kjonn) {
        int individNummber;
        if (erKvinne(kjonn)) {         //KVINNE: Individnummer avsluttes med partall
            individNummber = BiasedRandom.lagBunntungRandom(rangeStart, rangeSlutt);
            if (individNummber % 2 > 0){
                individNummber = individNummber + 1;
            }
        } else {                                  // MANN: Individnummer avsluttes med oddetall
            individNummber = BiasedRandom.lagBunntungRandom(rangeStart, rangeSlutt );
            if (individNummber % 2 == 0){
                individNummber = individNummber + 1;
            }
        }
        if (individNummber > rangeSlutt){
            individNummber = individNummber - 2;
        }

        StringBuilder individNummerBuilder = new StringBuilder(Integer.toString(individNummber));
        individNummerBuilder.reverse();
        if (individNummber < 10){
            individNummerBuilder.append(0);
        }
        if (individNummber < 100){
            individNummerBuilder.append(0);
        }
        return individNummerBuilder.reverse().toString();
    }

    private int hentForsteKontrollSiffer(String fnrCharacters) {
        return getKontrollSiffer(fnrCharacters, KONTROLL_SIFFER_C1);
    }

    private int getAndreKontrollSiffer(String fnrCharacters) {
        return getKontrollSiffer(fnrCharacters, KONTROLL_SIFFER_C2);
    }

    /**
     * <pre>
     * Lager kontrollsiffer for et fodselsnummer utifra satt kontrollsifferformel.
     * kontrollsiffer-1 = 11 - ((3*d + 7*d + 6*m +1*m + 8*å + 9*å + 4*i + 5*i + 2*i)  mod 11)
     * kontrollsiffer-2 = 11 - ((5*d + 4*d + 3*m + 2*m + 7*å + 6* å + 5*i + 4*i + 3*i + 2 *k1)  mod 11)
     * </pre>
     *
     * @param fnrCharacters:              Fodselsnummer
     * @param formelMultiplierSifferListe Array med tallene som skal multipliseres med fodselsnummer i kontrollsifferformelen
     * @return Kontrollsiffer
     */
    private int getKontrollSiffer(String fnrCharacters, int... formelMultiplierSifferListe) {
        int kontrollSiffer;
        int kontrollSifferFormelSum = 0;
        for (int i = 0; i < formelMultiplierSifferListe.length; i++) {
            int multiplierSiffer = formelMultiplierSifferListe[i];
            int fodselsnummerChar = Character.getNumericValue(fnrCharacters.charAt(i));
            kontrollSifferFormelSum = kontrollSifferFormelSum + (multiplierSiffer * fodselsnummerChar);
        }
        kontrollSiffer = 11 - (kontrollSifferFormelSum % 11);
        kontrollSiffer = kontrollSiffer == 11 ? 0 : kontrollSiffer;
        return kontrollSiffer;
    }

    private boolean isInYearRange(LocalDate date, int rangeYearStart, int rangeYearEnd) {
        return (date.getYear() >= rangeYearStart && date.getYear() <= rangeYearEnd);
    }

    private boolean erKvinne(Character kjonn){
        return kjonn == 'K';
    }
}
