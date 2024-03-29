package no.nav.tps.forvalteren.provider.rs.api.v1.endpoints.mapping;

import static no.nav.tps.forvalteren.domain.rs.skd.IdentType.FNR;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ma.glasnost.orika.MapperFacade;
import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.domain.rs.dolly.RsPersonBestillingKriteriumRequest;
import no.nav.tps.forvalteren.provider.rs.util.MapperTestUtils;
import no.nav.tps.forvalteren.service.command.testdata.opprett.DummyAdresseService;
import no.nav.tps.forvalteren.service.command.testdata.utils.HentDatoFraIdentService;

@RunWith(MockitoJUnitRunner.class)
public class PersonKriteriumMappingStrategyTest {

    private static final LocalDateTime TIMENOW = LocalDateTime.now();
    private static final String SIKKERHETSTILTAK = "tull";
    private static final String TYPESIKKERHET = "tøys";
    private static final String SPRAK = "EN";
    private static final String STATSBORGERSKAP = "SWE";
    private static final String IDENTTYPE = FNR.name();
    private static final String SPESREG = "KODE6";

    private MapperFacade mapperFacade;

    @Mock
    private DummyAdresseService dummyAdresseService;

    @Mock
    private HentDatoFraIdentService hentDatoFraIdentService;

    @InjectMocks
    private PersonKriteriumMappingStrategy personKriteriumMappingStrategy;

    @Before
    public void setup() {
        mapperFacade = MapperTestUtils.createMapperFacadeForMappingStrategy(personKriteriumMappingStrategy);
    }

    @Test
    public void matchVerificationOk() {

        when(hentDatoFraIdentService.extract(any())).thenReturn(LocalDateTime.now().minusYears(3));

        RsPersonBestillingKriteriumRequest bestilling = new RsPersonBestillingKriteriumRequest();
        bestilling.setTypeSikkerhetTiltak(TYPESIKKERHET);
        bestilling.setBeskrSikkerhetTiltak(SIKKERHETSTILTAK);
        bestilling.setDatoSprak(TIMENOW);
        bestilling.setSprakKode(SPRAK);
        bestilling.setStatsborgerskap(STATSBORGERSKAP);
        bestilling.setStatsborgerskapRegdato(TIMENOW);
        bestilling.setSpesreg(SPESREG);
        bestilling.setSpesregDato(TIMENOW);
        bestilling.setEgenAnsattDatoFom(TIMENOW);
        bestilling.setEgenAnsattDatoTom(TIMENOW);

        var person = Person.builder().ident("01020312345").build();
        mapperFacade.map(bestilling, person);

        assertThat(person.getIdenttype(), is(equalTo(IDENTTYPE)));
        assertThat(person.getStatsborgerskap().get(0).getStatsborgerskap(), is(equalTo(STATSBORGERSKAP)));
        assertThat(person.getStatsborgerskap().get(0).getStatsborgerskapRegdato(), is(equalTo(TIMENOW)));
        assertThat(person.getSprakKode(), is(equalTo(SPRAK)));
        assertThat(person.getDatoSprak(), is(equalTo(TIMENOW)));
        assertThat(person.getBeskrSikkerhetTiltak(), is(equalTo(SIKKERHETSTILTAK)));
        assertThat(person.getTypeSikkerhetTiltak(), is(equalTo(TYPESIKKERHET)));
        assertThat(person.getSpesreg(), is(equalTo(SPESREG)));
        assertThat(person.getSpesregDato(), is(equalTo(TIMENOW)));
        assertThat(person.getEgenAnsattDatoFom(), is(equalTo(TIMENOW)));
        assertThat(person.getEgenAnsattDatoTom(), is(equalTo(TIMENOW)));
    }
}