package no.nav.tps.vedlikehold.consumer.ws.fasit.queues;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import no.nav.tps.vedlikehold.consumer.ws.fasit.FasitClient;
import no.nav.tps.vedlikehold.domain.ws.fasit.Queue;
import no.nav.tps.vedlikehold.domain.ws.fasit.QueueManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class DefaultFasitMessageQueueConsumerTest {

    private final String REQUEST_QUEUE_ALIAS = "requestQueueAlias";
    private final String RESPONSE_QUEUE_ALIAS = "responseQueueAlias";
    private final String QUEUE_MANAGER_ALIAS = "queueManager";
    private final String ENVIRONMENT = "environment";
    private final String APPLICATION = "application";

    @Mock
    private FasitClient fasitClientMock;

    @Mock
    private QueueManager queueManagerMock;

    @Mock
    private Queue requestQueueMock;

    @Mock
    private Queue responseQueueMock;

    @Mock
    private FasitClient.Application applicationMock;

    @InjectMocks
    private DefaultFasitMessageQueueConsumer consumer = new DefaultFasitMessageQueueConsumer(APPLICATION, REQUEST_QUEUE_ALIAS, RESPONSE_QUEUE_ALIAS, QUEUE_MANAGER_ALIAS);

    @Before
    public void setUp() {
        when(fasitClientMock.getApplication(anyString(), anyString())).thenReturn(applicationMock);
        when(applicationMock.getQueueManager(QUEUE_MANAGER_ALIAS)).thenReturn(queueManagerMock);
        when(applicationMock.getQueue(REQUEST_QUEUE_ALIAS)).thenReturn(requestQueueMock);
        when(applicationMock.getQueue(RESPONSE_QUEUE_ALIAS)).thenReturn(responseQueueMock);
    }

    @Test
    public void getRequestQueueGetsQueueUsingTheRequestQueueAlias() {
        Queue result = consumer.getRequestQueue(ENVIRONMENT);

        assertThat(result, is(requestQueueMock));
    }

    @Test
    public void getResponseQueueGetsQueueUsingTheResponseQueueAlias() {
        Queue result = consumer.getResponseQueue(ENVIRONMENT);

        assertThat(result, is(responseQueueMock));
    }

    @Test
    public void getQueueManagerRetrievesManagerFromTheApplication() {
        QueueManager result = consumer.getQueueManager(ENVIRONMENT);

        assertThat(result, is(queueManagerMock));
    }

    @Test
    public void getQueueManagerRetrievesManagerWithAlias() {
        QueueManager result = consumer.getQueueManager(QUEUE_MANAGER_ALIAS, ENVIRONMENT);

        assertThat(result, is(queueManagerMock));
    }

}
