package no.nav.tps.forvalteren.service.command.testdata.restreq;

import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.utils.NullcheckUtil.nullcheckSetDefaultValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.glasnost.orika.MapperFacade;
import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.domain.rs.RsPersonKriterier;
import no.nav.tps.forvalteren.domain.rs.RsPersonKriteriumRequest;
import no.nav.tps.forvalteren.domain.rs.RsSimplePersonRequest;
import no.nav.tps.forvalteren.domain.rs.dolly.RsPersonBestillingKriteriumRequest;

@Service
public class ExtractOpprettKriterier {

    @Autowired
    private MapperFacade mapperFacade;

    public static RsPersonKriteriumRequest extractMainPerson(RsPersonBestillingKriteriumRequest req) {

        return RsPersonKriteriumRequest.builder()
                .personKriterierListe(singletonList(RsPersonKriterier.builder()
                        .antall(nonNull(req.getAntall()) && req.getAntall() > 0 ? req.getAntall() : 1)
                        .identtype(nullcheckSetDefaultValue(req.getIdenttype(), "FNR"))
                        .kjonn(nullcheckSetDefaultValue(req.getKjonn(), "U"))
                        .foedtEtter(nullcheckSetDefaultValue(req.getFoedtEtter(), LocalDateTime.now().minusYears(60)))
                        .foedtFoer(nullcheckSetDefaultValue(req.getFoedtFoer(), LocalDateTime.now().minusYears(30)))
                        .harMellomnavn(req.getHarMellomnavn())
                        .build()))
                .build();
    }

    public static RsPersonKriteriumRequest extractPartner(RsPersonBestillingKriteriumRequest hovedPersonRequest) {

        List<RsPersonKriterier> kriterier = new ArrayList();
        if (nonNull(hovedPersonRequest.getRelasjoner().getPartner())) {
            RsPersonKriterier kriterium = prepareKriterium(hovedPersonRequest.getRelasjoner().getPartner());
            kriterium.setFoedtEtter(nullcheckSetDefaultValue(kriterium.getFoedtEtter(), LocalDateTime.now().minusYears(60)));
            kriterium.setFoedtFoer(nullcheckSetDefaultValue(kriterium.getFoedtFoer(), LocalDateTime.now().minusYears(30)));
            kriterium.setHarMellomnavn(nullcheckSetDefaultValue(hovedPersonRequest.getRelasjoner().getPartner().getHarMellomnavn(), hovedPersonRequest.getHarMellomnavn()));
            kriterier.add(kriterium);
        }

        return RsPersonKriteriumRequest.builder()
                .personKriterierListe(kriterier)
                .build();
    }

    public static RsPersonKriteriumRequest extractBarn(RsPersonBestillingKriteriumRequest hovedpersonRequest) {

        List<RsPersonKriterier> kriterier = new ArrayList(hovedpersonRequest.getRelasjoner().getBarn().size());
        for (RsSimplePersonRequest barnRequest : hovedpersonRequest.getRelasjoner().getBarn()) {
            RsPersonKriterier kriterium = prepareKriterium(barnRequest);
            kriterium.setFoedtEtter(nullcheckSetDefaultValue(kriterium.getFoedtEtter(), LocalDateTime.now().minusYears(18)));
            kriterium.setHarMellomnavn(nullcheckSetDefaultValue(barnRequest.getHarMellomnavn(), hovedpersonRequest.getHarMellomnavn()));
            kriterier.add(kriterium);
        }

        return RsPersonKriteriumRequest.builder()
                .personKriterierListe(kriterier)
                .build();
    }

    private static RsPersonKriterier prepareKriterium(RsSimplePersonRequest req) {
        return RsPersonKriterier.builder()
                .antall(1)
                .identtype(nullcheckSetDefaultValue(req.getIdenttype(), "FNR"))
                .kjonn(nullcheckSetDefaultValue(req.getKjonn(), "U"))
                .foedtFoer(req.getFoedtFoer())
                .foedtEtter(req.getFoedtEtter())
                .build();
    }

    public List<Person> addExtendedKriterumValuesToPerson(RsPersonBestillingKriteriumRequest req, List<Person> hovedPersoner, List<Person> partnere, List<Person> barn) {

        hovedPersoner.forEach(person -> mapperFacade.map(req, person));

        if (nonNull(req.getRelasjoner().getPartner())) {
            partnere.forEach(partner -> {
                        req.getRelasjoner().getPartner().setBoadresse(req.getBoadresse());
                        req.getRelasjoner().getPartner().setPostadresse(req.getPostadresse());
                        mapperFacade.map(req.getRelasjoner().getPartner(), partner);
                        ammendDetailedPersonAttributes(req.getRelasjoner().getPartner(), partner);
                        partner.setSivilstand(req.getSivilstand());
                    }
            );
        }
        if (!req.getRelasjoner().getBarn().isEmpty()) {
            IntStream.range(0, barn.size()).forEach(i -> {
                req.getRelasjoner().getBarn().get(i).setBoadresse(req.getBoadresse());
                req.getRelasjoner().getBarn().get(i).setPostadresse(req.getPostadresse());
                mapperFacade.map(req.getRelasjoner().getBarn().get(i), barn.get(i));
                ammendDetailedPersonAttributes(req.getRelasjoner().getBarn().get(i), barn.get(i));
                barn.get(i).setSivilstand(null);
            });
        }

        List<Person> personer = new ArrayList<>();
        Stream.of(hovedPersoner, partnere, barn).forEach(personer::addAll);
        return personer;
    }

    private Person ammendDetailedPersonAttributes(RsSimplePersonRequest kriterier, Person person) {

        person.setStatsborgerskap(nullcheckSetDefaultValue(kriterier.getStatsborgerskap(), person.getStatsborgerskap()));
        person.setStatsborgerskapRegdato(nullcheckSetDefaultValue(kriterier.getStatsborgerskapRegdato(), person.getStatsborgerskapRegdato()));
        person.setSprakKode(nullcheckSetDefaultValue(kriterier.getSprakKode(), person.getSprakKode()));
        person.setDatoSprak(nullcheckSetDefaultValue(kriterier.getDatoSprak(), person.getDatoSprak()));

        return person;
    }
}