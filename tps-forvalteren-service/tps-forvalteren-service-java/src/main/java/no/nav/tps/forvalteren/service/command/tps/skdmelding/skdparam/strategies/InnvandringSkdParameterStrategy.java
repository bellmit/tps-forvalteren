package no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.strategies;

import static no.nav.tps.forvalteren.domain.service.tps.config.SkdConstants.TRANSTYPE_1;

import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.service.command.testdata.skd.SkdMeldingTrans1;
import no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.SkdParametersStrategy;
import no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.utils.ConvertDateToString;
import no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.utils.SetAdresse;
import no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.utils.StatsborgerskapEncoder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public abstract class InnvandringSkdParameterStrategy implements SkdParametersStrategy {

    @Autowired
    private StatsborgerskapEncoder statsborgerskapEncoder;

    @Autowired
    private SetAdresse setAdresse;

    private static final String AARSAKSKODE_FOR_INNVANDRING = "02";

    @Override
    public SkdMeldingTrans1 execute(Person person) {

        SkdMeldingTrans1 skdMeldingTrans1 = SkdMeldingTrans1.builder().tildelingskode(hentTildelingskode()).build();

        addSkdParametersExtractedFromPerson(skdMeldingTrans1, person);
        addDefaultParam(skdMeldingTrans1);

        return skdMeldingTrans1;
    }

    private void addSkdParametersExtractedFromPerson(SkdMeldingTrans1 skdMeldingTrans1, Person person) {
        skdMeldingTrans1.setFodselsdato(person.getIdent().substring(0, 6));
        skdMeldingTrans1.setPersonnummer(person.getIdent().substring(6, 11));
        skdMeldingTrans1.setFornavn(person.getFornavn());
        skdMeldingTrans1.setMellomnavn(person.getMellomnavn());
        skdMeldingTrans1.setSlektsnavn(person.getEtternavn());
        skdMeldingTrans1.setStatsborgerskap(statsborgerskapEncoder.encode(person.getStatsborgerskap()));

        skdParams.put(SkdConstants.SIVILSTAND, person.getSivilstand());
        skdParams.put(SkdConstants.INNVANDRET_FRA_LAND, person.getInnvandretFraLand());
        String yyyyMMdd = ConvertDateToString.yyyyMMdd(person.getRegdato());
        String hhMMss = ConvertDateToString.hhMMss(person.getRegdato());

        skdMeldingTrans1.setMaskintid(hhMMss);
        skdMeldingTrans1.setMaskindato( yyyyMMdd);
        skdMeldingTrans1.setRegDato( yyyyMMdd);
        skdMeldingTrans1.setRegdatoAdr( yyyyMMdd);
        skdMeldingTrans1.setFlyttedatoAdr( yyyyMMdd);
        skdMeldingTrans1.setFraLandRegdato(yyyyMMdd);
        skdMeldingTrans1.setFraLandFlyttedato(yyyyMMdd);
        skdMeldingTrans1.setRegdatoFamnr(yyyyMMdd);

        setAdresse.execute(skdMeldingTrans1, person);
        addSpesreg(skdMeldingTrans1, person);
    }

    private void addSpesreg( SkdMeldingTrans1 skdMeldingTrans1, Person person) {
        if (person.getSpesreg() != null) {
            skdMeldingTrans1.setSpesRegType( person.getSpesreg());
        }
        LocalDateTime spesregDato = person.getSpesregDato();
        if (spesregDato != null) {
            skdMeldingTrans1.setDatoSpesRegType( String.format("%04d%02d%02d", spesregDato.getYear(), spesregDato.getMonthValue(), spesregDato.getDayOfMonth()));
        }
    }

    private void addDefaultParam(SkdMeldingTrans1 skdMeldingTrans1) {
        skdMeldingTrans1.setAarsakskode(AARSAKSKODE_FOR_INNVANDRING);
        skdMeldingTrans1.setInnvandretFraLand( "001");
        skdMeldingTrans1.setFamilienummer("08096740140");
        skdMeldingTrans1.setPersonkode("1");
        skdMeldingTrans1.setSivilstand("1");
        skdMeldingTrans1.setTranstype(TRANSTYPE_1);
        skdMeldingTrans1.setStatuskode( "1");
    }
    
}
