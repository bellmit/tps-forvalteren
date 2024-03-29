package no.nav.tps.forvalteren.service.command.foedselsmelding;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.domain.rs.skd.RsTpsFoedselsmeldingRequest;
import no.nav.tps.forvalteren.service.command.testdata.opprett.EkstraherIdenterFraTestdataRequests;
import no.nav.tps.forvalteren.service.command.testdata.opprett.OpprettPersonerService;
import no.nav.tps.forvalteren.service.command.testdata.opprett.PersonNameService;
import no.nav.tps.forvalteren.service.command.testdata.opprett.RandomAdresseService;
import no.nav.tps.forvalteren.service.command.testdata.opprett.TestdataIdenterFetcher;

@RunWith(MockitoJUnitRunner.class)
public class OpprettPersonMedEksisterendeForeldreServiceTest {

    private static final String IDENT_MOR = "12129012345";
    private static final String IDENT_FAR = "13118912345";

    @Mock
    private TestdataIdenterFetcher testdataIdenterFetcher;

    @Mock
    private EkstraherIdenterFraTestdataRequests ekstraherIdenterFraTestdataRequests;

    @Mock
    private OpprettPersonerService opprettPersonerService;

    @Mock
    private PersonNameService personNameService;

    @Mock
    private RandomAdresseService randomAdresseOnPerson;

    @InjectMocks
    private OpprettPersonMedEksisterendeForeldreService opprettPersonService;

    private RsTpsFoedselsmeldingRequest rsTpsFoedselsmeldingRequest;

    @Before
    public void setup() {
        when(opprettPersonerService.execute(anyCollection())).thenReturn(Arrays.asList(new Person()));
    }

    @Test
    public void genererPersonMedRelasjonOK() {

        rsTpsFoedselsmeldingRequest = RsTpsFoedselsmeldingRequest.builder()
                .foedselsdato(LocalDateTime.now())
                .identMor(IDENT_MOR)
                .identFar(IDENT_FAR)
                .miljoer(newHashSet("u6"))
                .build();

        Person result = opprettPersonService.execute(rsTpsFoedselsmeldingRequest);

        assertThat(result.getRelasjoner().get(0).getPersonRelasjonMed().getIdent(), is(equalTo(IDENT_MOR)));
        assertThat(result.getRelasjoner().get(1).getPersonRelasjonMed().getIdent(), is(equalTo(IDENT_FAR)));
    }
}