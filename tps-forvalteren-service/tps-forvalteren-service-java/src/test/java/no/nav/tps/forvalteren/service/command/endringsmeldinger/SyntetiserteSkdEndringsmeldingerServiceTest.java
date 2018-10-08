package no.nav.tps.forvalteren.service.command.endringsmeldinger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import no.nav.tps.forvalteren.consumer.rs.identpool.IdentPoolClient;
import no.nav.tps.forvalteren.domain.rs.skd.RsMeldingstype1Felter;

@RunWith(MockitoJUnitRunner.class)
public class SyntetiserteSkdEndringsmeldingerServiceTest {
    
    @Mock
    private IdentPoolClient identPoolClient;
    
    @InjectMocks
    private SyntetiserteSkdEndringsmeldingerService service;
    
    @Test
    public void shouldIncertNewIdentsIntoSkdInnvandringAndFoedselsmelding() {
        RsMeldingstype1Felter foedselsmelding = new RsMeldingstype1Felter();
        foedselsmelding.setAarsakskode("01");
        RsMeldingstype1Felter innvandringsmelding = new RsMeldingstype1Felter();
        innvandringsmelding.setAarsakskode("02");
        RsMeldingstype1Felter flyttemelding = new RsMeldingstype1Felter();
        flyttemelding.setAarsakskode("24");
        
        final String expectedFNR1 = "11111111111";
        final String expectedFNR2 = "22222222222";
        
        when(identPoolClient.hentNyeIdenter(any())).thenReturn(Arrays.asList(expectedFNR1, expectedFNR2));
        
        final List<String> nyeIdenter = service.settInnNyeIdenterIAktuelleMeldinger(Arrays.asList(foedselsmelding, innvandringsmelding, flyttemelding));
        
        assertEquals(2, nyeIdenter.size());
        assertEquals(expectedFNR1, foedselsmelding.getFodselsdato() + foedselsmelding.getPersonnummer());
        assertEquals(expectedFNR2, innvandringsmelding.getFodselsdato() + innvandringsmelding.getPersonnummer());
        assertNull(flyttemelding.getFodselsdato());
        assertNull(flyttemelding.getPersonnummer());
    }
}