package no.nav.tps.vedlikehold.service.command.tps.transformation.request;

import com.fasterxml.jackson.xml.XmlMapper;
import no.nav.tps.vedlikehold.domain.service.user.User;
import no.nav.tps.vedlikehold.domain.service.tps.Request;
import no.nav.tps.vedlikehold.domain.service.tps.TpsSystemInfo;
import no.nav.tps.vedlikehold.domain.service.tps.servicerutiner.requests.TpsRequestContext;
import no.nav.tps.vedlikehold.domain.service.tps.servicerutiner.requests.TpsServiceRoutineEndringRequest;
import no.nav.tps.vedlikehold.domain.service.tps.servicerutiner.transformers.request.EndringsmeldingRequestTransform;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EndringsmeldingRequestTransformStrategyTest {


    private static final String XML_PROPERTIES_PREFIX  = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><sfePersonData><sfeAjourforing>";
    private static final String XML_PROPERTIES_POSTFIX = "</sfeAjourforing></sfePersonData>";

    private static final String XML = "xml";
    private static final String SYS_INFO = "sysinfo";


    @Mock
    private XmlMapper xmlMapperMock;

    @InjectMocks
    private EndringsmeldingRequestTransformStrategy strategy;

    @Test
    public void isSupportedReturnsTrueforEndringsMeldingRequestTransform() {
        EndringsmeldingRequestTransform transform = new EndringsmeldingRequestTransform();

        boolean isSupported = strategy.isSupported(transform);

        assertThat(isSupported, is(true));
    }

    @Test
    public void executeUpdatesRequestXmlWithWrappingElements() throws IOException {
        when(xmlMapperMock.writeValueAsString(any(TpsSystemInfo.class))).thenReturn(SYS_INFO);

        TpsRequestContext context = new TpsRequestContext();
        context.setUser(new User("name", "username", null));

        TpsServiceRoutineEndringRequest serviceRoutine = new TpsServiceRoutineEndringRequest();
        serviceRoutine.setKilde("kilde");

        Request request = new Request();
        request.setXml(XML);
        request.setContext(context);
        request.setRoutineRequest(serviceRoutine);

        strategy.execute(request, null);

        assertThat(request.getXml(), is(equalTo(XML_PROPERTIES_PREFIX + SYS_INFO + XML + XML_PROPERTIES_POSTFIX)));

    }

}