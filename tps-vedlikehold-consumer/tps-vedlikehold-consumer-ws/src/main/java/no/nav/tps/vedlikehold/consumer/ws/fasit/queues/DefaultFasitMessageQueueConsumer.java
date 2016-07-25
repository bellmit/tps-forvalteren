package no.nav.tps.vedlikehold.consumer.ws.fasit.queues;

import no.nav.tps.vedlikehold.consumer.ws.fasit.FasitClient;
import no.nav.tps.vedlikehold.domain.ws.fasit.Queue;
import no.nav.tps.vedlikehold.domain.ws.fasit.QueueManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Convenience class for exposing MQ resources.
 * Connects to the application's Fasit resources and exposes its request and response queues.
 *
 * @author Øyvind Grimnes, Visma Consulting AS
 */

public class DefaultFasitMessageQueueConsumer implements FasitMessageQueueConsumer {

    @Autowired
    private FasitClient fasitClient;

    private String applicationName;

    private String requestQueueAlias;
    private String responseQueueAlias;
    private String queueManagerAlias;

    public DefaultFasitMessageQueueConsumer(String application, String requestQueueAlias, String responseQueueAlias, String queueManagerAlias) {
        this.applicationName = application;
        this.requestQueueAlias = requestQueueAlias;
        this.responseQueueAlias = responseQueueAlias;
        this.queueManagerAlias = queueManagerAlias;
    }

    @Override
    public Queue getRequestQueue(String environment) {
        return getQueue(requestQueueAlias, environment);
    }

    @Override
    public Queue getResponseQueue(String environment) {
        return getQueue(responseQueueAlias, environment);
    }

    @Override
    public Queue getQueue(String alias, String environment) {
        FasitClient.Application application = getApplication(environment);
        return application.getQueue(alias);
    }

    public QueueManager getQueueManager(String environment) {
        FasitClient.Application application = getApplication(environment);
        return application.getQueueManager(queueManagerAlias);
    }

    @Override
    public QueueManager getQueueManager(String alias, String environment) {
        FasitClient.Application application = getApplication(environment);
        return application.getQueueManager(alias);
    }

    private FasitClient.Application getApplication(String environment) {
        return fasitClient.getApplication(applicationName, environment);
    }
}
