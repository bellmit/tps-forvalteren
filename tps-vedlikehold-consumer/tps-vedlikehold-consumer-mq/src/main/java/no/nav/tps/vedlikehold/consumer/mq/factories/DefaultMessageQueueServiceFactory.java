package no.nav.tps.vedlikehold.consumer.mq.factories;

import no.nav.tps.vedlikehold.consumer.mq.services.DefaultMessageQueueService;
import no.nav.tps.vedlikehold.consumer.mq.strategies.ConnectionFactoryStrategy;
import no.nav.tps.vedlikehold.consumer.mq.strategies.QueueManagerConnectionFactoryStrategy;
import no.nav.tps.vedlikehold.consumer.ws.fasit.queues.FasitMessageQueueConsumer;
import no.nav.tps.vedlikehold.domain.ws.fasit.Queue;
import no.nav.tps.vedlikehold.domain.ws.fasit.QueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * Consumes information from Fasit and produces MessageQueueServices
 *
 * @author Øyvind Grimnes, Visma Consulting AS
 */

@Component
public class DefaultMessageQueueServiceFactory implements MessageQueueServiceFactory {

    private static final String MESSAGE_QUEUE_MANAGER_ALIAS = "mqGateway";

    @Autowired
    private FasitMessageQueueConsumer fasitMessageQueueConsumer;

    @Autowired
    private ConnectionFactoryFactory connectionFactoryFactory;

    /**
     * Instantiates a new MessageQueueService in the specified environment
     *
     * @param environment in which the messages will be exchanged
     * @return A MessageQueueService providing communication with an MQ in the specified environment
     * @throws JMSException
     */
    public DefaultMessageQueueService createMessageQueueService(String environment) throws JMSException {
        QueueManager queueManager = fasitMessageQueueConsumer.getQueueManager(MESSAGE_QUEUE_MANAGER_ALIAS, environment);
        Queue requestQueue        = fasitMessageQueueConsumer.getRequestQueue(environment);
        Queue responseQueue       = fasitMessageQueueConsumer.getResponseQueue(environment);

        ConnectionFactoryStrategy connectionFactoryStrategy = new QueueManagerConnectionFactoryStrategy(queueManager);

        ConnectionFactory connectionFactory = connectionFactoryFactory.createConnectionFactory(connectionFactoryStrategy);

        return new DefaultMessageQueueService(
                requestQueue.getName(),
                responseQueue.getName(),
                connectionFactory
        );
    }

}