package no.nav.tps.vedlikehold.consumer.ws.tpsws.egenansatt;

import no.nav.tjeneste.pip.pipegenansatt.v1.PipEgenAnsattPortType;
import no.nav.tjeneste.pip.pipegenansatt.v1.meldinger.ErEgenAnsattEllerIFamilieMedEgenAnsattRequest;
import no.nav.tjeneste.pip.pipegenansatt.v1.meldinger.ErEgenAnsattEllerIFamilieMedEgenAnsattResponse;
import no.nav.tps.vedlikehold.consumer.ws.tpsws.exceptions.FNrEmptyException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Tobias Hansen (Visma Consulting AS).
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultEgenAnsattConsumerTest {
    private static final String THE_DATABASE_DOES_NOT_ANSWER_ERROR = "Databasen svarer ikke";
    private static final String TEST_FNR = "11223344556";

    @InjectMocks
    private DefaultEgenAnsattConsumer egenAnsattConsumer;

    @Mock
    private PipEgenAnsattPortType egenAnsattPortType;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void isEgenAnsattThrowsFNrEmptyExceptionCalledWithEmptyString(){
        expectedException.expect(FNrEmptyException.class);

        egenAnsattConsumer.isEgenAnsatt("");
    }

    @Test
    public void isEgenAnsattThrowsFNrEmptyExceptionCalledWithNull(){
        expectedException.expect(FNrEmptyException.class);

        egenAnsattConsumer.isEgenAnsatt(null);
    }

    @Test
    public void pingReturnsTrueWhenErEgenAnsattRespondsNormally() throws Exception {
        when(egenAnsattPortType.erEgenAnsattEllerIFamilieMedEgenAnsatt((ErEgenAnsattEllerIFamilieMedEgenAnsattRequest)
                any(ErEgenAnsattEllerIFamilieMedEgenAnsattRequest.class)))
                .thenReturn(new ErEgenAnsattEllerIFamilieMedEgenAnsattResponse());

        boolean result = egenAnsattConsumer.ping();

        assertThat(result, is(equalTo(true)));
    }

    @Test
    public void pingThrowsExceptionWhenIsEgenAnsattThrowsException() throws Exception {
        RuntimeException thrownException = new RuntimeException(THE_DATABASE_DOES_NOT_ANSWER_ERROR);
        when(egenAnsattPortType.erEgenAnsattEllerIFamilieMedEgenAnsatt((ErEgenAnsattEllerIFamilieMedEgenAnsattRequest)
                any(ErEgenAnsattEllerIFamilieMedEgenAnsattRequest.class)))
                .thenThrow(thrownException);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(THE_DATABASE_DOES_NOT_ANSWER_ERROR);

        egenAnsattConsumer.ping();
    }

    @Test
    public void hentDiskresjonskodeRequestIsSentWithCorrectFNr() throws java.lang.Exception {
        when(egenAnsattPortType.erEgenAnsattEllerIFamilieMedEgenAnsatt((ErEgenAnsattEllerIFamilieMedEgenAnsattRequest)
                any(ErEgenAnsattEllerIFamilieMedEgenAnsattRequest.class)))
                .thenReturn(new ErEgenAnsattEllerIFamilieMedEgenAnsattResponse());

        egenAnsattConsumer.isEgenAnsatt(TEST_FNR);

        ArgumentCaptor<ErEgenAnsattEllerIFamilieMedEgenAnsattRequest> captor = ArgumentCaptor.forClass(ErEgenAnsattEllerIFamilieMedEgenAnsattRequest.class);
        verify(egenAnsattPortType).erEgenAnsattEllerIFamilieMedEgenAnsatt(captor.capture());

        assertThat(captor.getValue().getIdent(), is(equalTo(TEST_FNR)));
    }
}