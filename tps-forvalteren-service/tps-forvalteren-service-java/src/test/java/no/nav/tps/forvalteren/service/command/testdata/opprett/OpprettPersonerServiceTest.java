package no.nav.tps.forvalteren.service.command.testdata.opprett;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.service.command.testdata.utils.HentDatoFraIdentService;
import no.nav.tps.forvalteren.service.command.testdata.utils.HentKjoennFraIdent;
import no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.utils.LandkodeEncoder;

@RunWith(MockitoJUnitRunner.class)
public class OpprettPersonerServiceTest {

    private List<String> identerInput = new ArrayList<>();

    private String identDummy1 = "111111111111";
    private String identDummyDNR = "411111111111";
    private String identDummyBNR = "11311111111";

    @Mock
    private HentKjoennFraIdent hentKjoennFraIdentMock;

    @Mock
    private HentDatoFraIdentService hentDatoFraIdentService;

    @Mock
    private LandkodeEncoder landkodeEncoder;

    @InjectMocks
    private OpprettPersonerService opprettPersonerService;

    @Before
    public void setup() {
        when(hentKjoennFraIdentMock.execute(anyString())).thenReturn('M');
    }

    @Test
    public void opprettPersonerOppretterRiktigAntall(){
        identerInput.addAll(Arrays.asList(identDummy1,identDummyDNR,identDummyBNR));
        List<Person> personer = opprettPersonerService.execute(identerInput);

        assertThat(personer.size() == 3, is(true));
    }

    @Test
    public void identInputErSammeIdentSomPersonFaar() {
        identerInput.add(identDummy1);
        List<Person> personer = opprettPersonerService.execute(identerInput);
        assertThat(personer.get(0).getIdent(), is(identDummy1));
    }

    @Test
    public void hvisKjonnErMannSaErPersonMann() {
        identerInput.add(identDummy1);
        List<Person> personer = opprettPersonerService.execute(identerInput);
        assertThat(personer.get(0).getKjonn(), is('M'));
    }

    @Test
    public void hvisKjonnErKvinneSaErPersonKvinne() {
        when(hentKjoennFraIdentMock.execute(anyString())).thenReturn('K');
        identerInput.add(identDummy1);
        List<Person> personer = opprettPersonerService.execute(identerInput);
        assertThat(personer.get(0).getKjonn(), is('K'));
    }

    @Test
    public void hvisIdentErDNRSaaHarPersonDNR(){
        identerInput.add(identDummyDNR);
        List<Person> personer = opprettPersonerService.execute(identerInput);
        assertThat(personer.get(0).getIdenttype(), is("DNR"));
    }

    @Test
    public void hvisIdentErBNRSaaHarPersonBNR(){
        identerInput.add(identDummyBNR);
        List<Person> personer = opprettPersonerService.execute(identerInput);
        assertThat(personer.get(0).getIdenttype(), is("BNR"));
    }

    @Test
    public void hvisIdentErFNRSaaHarPersonFNR(){
        identerInput.add(identDummy1);
        List<Person> personer = opprettPersonerService.execute(identerInput);
        assertThat(personer.get(0).getIdenttype(), is("FNR"));
    }
}