package no.nav.tps.vedlikehold.service.command.tps;

import no.nav.tps.vedlikehold.common.java.message.MessageProvider;
import no.nav.tps.vedlikehold.consumer.mq.consumers.MessageQueueConsumer;
import no.nav.tps.vedlikehold.consumer.mq.factories.MessageQueueServiceFactory;
import no.nav.tps.vedlikehold.consumer.ws.fasit.config.FasitConstants;
import no.nav.tps.vedlikehold.domain.service.command.User.User;
import no.nav.tps.vedlikehold.domain.service.command.tps.TpsMessage;
import no.nav.tps.vedlikehold.domain.service.command.tps.TpsSystemInfo;
import no.nav.tps.vedlikehold.domain.service.command.tps.ajourforing.requests.TpsRequestEndringsmelding;
import no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.requests.TpsRequestServiceRoutine;
import no.nav.tps.vedlikehold.provider.rs.security.user.UserContextHolder;
import no.nav.tps.vedlikehold.service.command.authorisation.TpsAuthorisationService;
import no.nav.tps.vedlikehold.service.command.exceptions.HttpUnauthorisedException;
import no.nav.tps.vedlikehold.service.command.tps.utils.TpsRequestXmlCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.IOException;

@Service
public class DefaultTpsRequestService implements TpsRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTpsRequestService.class);

    @Autowired
    private MessageQueueServiceFactory messageQueueServiceFactory;

    @Autowired
    private TpsRequestXmlCreator tpsRequestXmlCreator;

    @Autowired
    private TpsAuthorisationService tpsAuthorisationService;

    @Autowired
    private MessageProvider messageProvider;

    /**
     * Send a request to TPS using asynchronous message queues
     *
     * @param tpsRequest TPS request object
     * @return A String representing the XML-response from TPS
     * @throws JMSException failed to send the message
     */
    @Override
    public String executeServiceRutineRequest(TpsRequestServiceRoutine tpsRequest, TpsMessage tpsMessage) throws IOException, JMSException, HttpUnauthorisedException {
        if (!tpsAuthorisationService.userIsAuthorisedToReadPersonInEnvironment(tpsMessage, tpsRequest)) {
            throw new HttpUnauthorisedException(messageProvider.get("rest.service.request.exception.Unauthorized"), "api/v1/service/" + tpsRequest.getServiceRutinenavn());
        }
        try {
            MessageQueueConsumer messageQueueConsumer = messageQueueServiceFactory.createMessageQueueService(tpsRequest.getEnvironment(), FasitConstants.REQUEST_QUEUE_SERVICE_RUTINE_ALIAS);
            String xmlRequestMessage = tpsRequestXmlCreator.createXmlTpsRequestServiceRutine(tpsRequest);
            String responseXml = messageQueueConsumer.sendMessage(xmlRequestMessage);
            return responseXml;
        } catch (JMSException exception) {
            LOGGER.error("Failed to connect to MQ with exception: {}", exception.toString());
            throw exception;
        }
    }

    @Override
    public String executeEndringsmeldingRequest(TpsRequestEndringsmelding tpsRequest, TpsSystemInfo tpsSystemInfo) throws IOException, JMSException {
        try {
            MessageQueueConsumer messageQueueConsumer = messageQueueServiceFactory.createMessageQueueService(tpsRequest.getEnvironment(), FasitConstants.REQUEST_QUEUE_ENDRINGSMELDING_ALIAS);
            String xmlRequestMessage = tpsRequestXmlCreator.createXmlTpsRequestEndringsmelding(tpsRequest, tpsSystemInfo);
            String responseXml = messageQueueConsumer.sendMessage(xmlRequestMessage);
            return responseXml;
        } catch (JMSException exception) {
            LOGGER.error("Failed to connect to MQ with exception: {}", exception.toString());
            throw exception;
        }
    }
}
