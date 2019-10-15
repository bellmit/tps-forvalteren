package no.nav.tps.forvalteren.service.command.testdata.restreq;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ma.glasnost.orika.MapperFacade;
import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.domain.rs.RsPartnerRequest;
import no.nav.tps.forvalteren.domain.rs.RsPersonKriteriumRequest;
import no.nav.tps.forvalteren.domain.rs.RsSimplePersonRequest;
import no.nav.tps.forvalteren.domain.rs.RsSimpleRelasjoner;
import no.nav.tps.forvalteren.domain.rs.dolly.RsPersonBestillingKriteriumRequest;
import no.nav.tps.forvalteren.service.command.testdata.opprett.DummyAdresseService;
import no.nav.tps.forvalteren.service.command.testdata.opprett.RandomAdresseService;
import no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.utils.LandkodeEncoder;

@RunWith(MockitoJUnitRunner.class)
public class ExtractOpprettKriterierTest {

    private static final LocalDateTime FOEDT_ETTER = LocalDateTime.of(1950, 1, 1, 0, 0);
    private static final LocalDateTime FOEDT_FOER = LocalDateTime.of(2018, 12, 18, 0, 0);
    private static final int ANTALL = 100;
    private static final String KJOENN = "M";
    private static final String IDENTTYPE = "DNR";

    @Mock
    private MapperFacade mapperFacade;

    @Mock
    private RandomAdresseService randomAdresseService;

    @Mock
    private DummyAdresseService dummyAdresseService;

    @Mock
    private LandkodeEncoder landkodeEncoder;

    @InjectMocks
    private ExtractOpprettKriterier extractOpprettKriterier;

    @Test
    public void extractMainPersonAllParamsSet() {
        RsPersonBestillingKriteriumRequest bestilling = new RsPersonBestillingKriteriumRequest();
        bestilling.setAntall(ANTALL);
        bestilling.setKjonn(KJOENN);
        bestilling.setFoedtEtter(FOEDT_ETTER);
        bestilling.setFoedtFoer(FOEDT_FOER);
        bestilling.setIdenttype(IDENTTYPE);

        RsPersonKriteriumRequest target = extractOpprettKriterier.extractMainPerson(bestilling);

        assertThat(target.getPersonKriterierListe().get(0).getAntall(), is(equalTo(ANTALL)));
        assertThat(target.getPersonKriterierListe().get(0).getKjonn(), is(equalTo(KJOENN)));
        assertThat(target.getPersonKriterierListe().get(0).getIdenttype(), is(equalTo(IDENTTYPE)));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtFoer(), is(equalTo(FOEDT_FOER)));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtEtter(), is(equalTo(FOEDT_ETTER)));
    }

    @Test
    public void extractMainPersonNoParamsSet() {

        RsPersonKriteriumRequest target = extractOpprettKriterier.extractMainPerson(new RsPersonBestillingKriteriumRequest());

        assertThat(target.getPersonKriterierListe().get(0).getAntall(), is(equalTo(1)));
        assertThat(target.getPersonKriterierListe().get(0).getKjonn(), is(equalTo("U")));
        assertThat(target.getPersonKriterierListe().get(0).getIdenttype(), is(equalTo("FNR")));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtFoer().format(ISO_LOCAL_DATE),
                greaterThanOrEqualTo(LocalDateTime.now().minusYears(30).format(ISO_LOCAL_DATE)));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtEtter().format(ISO_LOCAL_DATE),
                greaterThanOrEqualTo(LocalDateTime.now().minusYears(60).format(ISO_LOCAL_DATE)));
    }

    @Test
    public void extractPartnerAllParamsSet() {

        RsPartnerRequest partnerRequest = new RsPartnerRequest();
        partnerRequest.setKjonn(KJOENN);
        partnerRequest.setFoedtEtter(FOEDT_ETTER);
        partnerRequest.setFoedtFoer(FOEDT_FOER);
        partnerRequest.setIdenttype(IDENTTYPE);

        RsPersonBestillingKriteriumRequest request = new RsPersonBestillingKriteriumRequest();
        request.setRelasjoner(RsSimpleRelasjoner.builder()
                .partner(singletonList(partnerRequest))
                .build());

        RsPersonKriteriumRequest target = extractOpprettKriterier.extractPartner(request);

        assertThat(target.getPersonKriterierListe().get(0).getAntall(), is(equalTo(1)));
        assertThat(target.getPersonKriterierListe().get(0).getKjonn(), is(equalTo(KJOENN)));
        assertThat(target.getPersonKriterierListe().get(0).getIdenttype(), is(equalTo(IDENTTYPE)));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtFoer(), is(equalTo(FOEDT_FOER)));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtEtter(), is(equalTo(FOEDT_ETTER)));
    }

    @Test
    public void extractPartnerNoParamsSet() {

        RsPersonBestillingKriteriumRequest request = new RsPersonBestillingKriteriumRequest();
        request.setRelasjoner(RsSimpleRelasjoner.builder()
                .partner(singletonList(new RsPartnerRequest()))
                .build());

        RsPersonKriteriumRequest target = extractOpprettKriterier.extractPartner(request);

        assertThat(target.getPersonKriterierListe().get(0).getAntall(), is(equalTo(1)));
        assertThat(target.getPersonKriterierListe().get(0).getKjonn(), is(equalTo("U")));
        assertThat(target.getPersonKriterierListe().get(0).getIdenttype(), is(equalTo("FNR")));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtFoer().format(ISO_LOCAL_DATE),
                greaterThanOrEqualTo(LocalDateTime.now().minusYears(30).format(ISO_LOCAL_DATE)));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtEtter().format(ISO_LOCAL_DATE),
                greaterThanOrEqualTo(LocalDateTime.now().minusYears(60).format(ISO_LOCAL_DATE)));
    }

    @Test
    public void extractBarnAllParamsSet() {

        RsPersonBestillingKriteriumRequest request = new RsPersonBestillingKriteriumRequest();
        request.setRelasjoner(RsSimpleRelasjoner.builder()
                .barn(singletonList(RsSimplePersonRequest.builder()
                        .kjonn(KJOENN)
                        .foedtEtter(FOEDT_ETTER)
                        .foedtFoer(FOEDT_FOER)
                        .identtype(IDENTTYPE)
                        .build()))
                .build());

        RsPersonKriteriumRequest target = extractOpprettKriterier.extractBarn(request);

        assertThat(target.getPersonKriterierListe().get(0).getAntall(), is(equalTo(1)));
        assertThat(target.getPersonKriterierListe().get(0).getKjonn(), is(equalTo(KJOENN)));
        assertThat(target.getPersonKriterierListe().get(0).getIdenttype(), is(equalTo(IDENTTYPE)));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtFoer(), is(equalTo(FOEDT_FOER)));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtEtter(), is(equalTo(FOEDT_ETTER)));
    }

    @Test
    public void extractBarnNoParamsSet() {

        RsPersonBestillingKriteriumRequest request = new RsPersonBestillingKriteriumRequest();
        request.setRelasjoner(RsSimpleRelasjoner.builder()
                .barn(singletonList(RsSimplePersonRequest.builder().build()))
                .build());

        RsPersonKriteriumRequest target = extractOpprettKriterier.extractBarn(request);

        assertThat(target.getPersonKriterierListe().get(0).getAntall(), is(equalTo(1)));
        assertThat(target.getPersonKriterierListe().get(0).getKjonn(), is(equalTo("U")));
        assertThat(target.getPersonKriterierListe().get(0).getIdenttype(), is(equalTo("FNR")));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtFoer(), is(notNullValue()));
        assertThat(target.getPersonKriterierListe().get(0).getFoedtEtter().format(ISO_LOCAL_DATE),
                greaterThanOrEqualTo(LocalDateTime.now().minusYears(60).format(ISO_LOCAL_DATE)));
    }

    @Test
    public void extractDollyParametere() {

        RsPersonBestillingKriteriumRequest kriterier = new RsPersonBestillingKriteriumRequest();
        List<Person> hovedperson = singletonList(Person.builder().build());
        List<Person> partner = singletonList(Person.builder().build());
        List<Person> barn = singletonList(Person.builder().build());

        List<Person> personer = extractOpprettKriterier.addExtendedKriterumValuesToPerson(kriterier, hovedperson, partner, barn);
        assertThat(personer, hasSize(3));
    }
}