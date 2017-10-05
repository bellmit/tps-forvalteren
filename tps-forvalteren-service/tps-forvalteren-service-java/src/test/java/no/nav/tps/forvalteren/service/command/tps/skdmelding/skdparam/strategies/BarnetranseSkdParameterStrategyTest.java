package no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.strategies;

import static no.nav.tps.forvalteren.domain.test.provider.PersonProvider.aFemalePerson;
import static no.nav.tps.forvalteren.domain.test.provider.PersonProvider.aMalePerson;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.domain.jpa.Relasjon;
import no.nav.tps.forvalteren.repository.jpa.RelasjonRepository;
import no.nav.tps.forvalteren.service.command.testdata.FinnBarnTilForeldreFraRelasjoner;

@RunWith(MockitoJUnitRunner.class)
public class BarnetranseSkdParameterStrategyTest {

    @InjectMocks
    private BarnetranseSkdParameterStrategy barnetranseSkdParameterStrategy;

    @Mock
    private RelasjonRepository relasjonRepository;

    @Mock
    private FinnBarnTilForeldreFraRelasjoner finnBarnTilForeldreFraRelasjoner;

    private Person foreldre = aMalePerson().build();
    private List<Person> barn = new ArrayList<>();
    private List<Relasjon> foreldreBarnRelasjoner = new ArrayList<>();
    private Map<String, String> skdParams;

    @Before
    public void setup() {
        barn.add(aFemalePerson().build());
        barn.add(aMalePerson().build());
        Relasjon relasjon = new Relasjon(1L, foreldre, barn.get(0), "BARN");
        Relasjon relasjon2 = new Relasjon(2L, foreldre, barn.get(1), "BARN");
        foreldreBarnRelasjoner.add(relasjon);
        foreldreBarnRelasjoner.add(relasjon2);

        when(relasjonRepository.findByPersonAndRelasjonTypeNavn(foreldre, "BARN")).thenReturn(foreldreBarnRelasjoner);
        when(finnBarnTilForeldreFraRelasjoner.execute(foreldreBarnRelasjoner)).thenReturn(barn);

        skdParams = barnetranseSkdParameterStrategy.execute(foreldre);
    }

    @Test
    public void checkThatDefaultFieldsAreAdded() {
        assertThat(skdParams, hasKey("T2-MASKINDATO"));
        assertThat(skdParams, hasKey("T2-MASKINTID"));
        assertThat(skdParams, hasEntry("T2-TRANSTYPE", "2"));
        assertThat(skdParams, hasEntry("T2-AARSAKSKODE", "98"));
    }

    @Test
    public void checkThatForeldreIsAdded() {
        assertThat(skdParams, hasEntry("T2-FODSELSNR", foreldre.getIdent()));
    }

    @Test
    public void checkThatBarnAreAdded() {
        for (int counter = 0; counter < barn.size(); counter++) {
            String fodsdatoKey = "T2-BARN-FODSDATO" + counter;
            String fodsdatoValue = barn.get(counter).getIdent().substring(0,6);

            String persnrKey = "T2-BARN-PERSNR" + counter;
            String persnrValue = barn.get(counter).getIdent().substring(6);

            assertThat(skdParams, hasEntry(fodsdatoKey, fodsdatoValue));
            assertThat(skdParams, hasEntry(persnrKey, persnrValue));
        }
    }

}