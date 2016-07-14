package no.nav.tps.vedlikehold.consumer.mq.factories;

import no.nav.tps.vedlikehold.consumer.mq.services.MessageQueueService;

import javax.jms.JMSException;

/**
 * @author Øyvind Grimnes, Visma Consulting AS
 */
public interface MessageQueueServiceFactory {
    MessageQueueService createMessageQueueService(String environment) throws JMSException;
}
