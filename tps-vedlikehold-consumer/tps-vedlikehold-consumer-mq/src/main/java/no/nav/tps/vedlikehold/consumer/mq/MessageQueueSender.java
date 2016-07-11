package no.nav.tps.vedlikehold.consumer.mq;

import no.nav.tps.vedlikehold.consumer.ws.fasit.queue.FasitMessageQueueConsumer;
import no.nav.tps.vedlikehold.domain.ws.fasit.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.SessionCallback;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author Øyvind Grimnes, Visma Consulting AS
 */

@Component
public class MessageQueueSender {

    private final long timeout = 5000;

    @Autowired
    ConfigurableApplicationContext context;


    public void sendMessage(final String message, String environment) {

        FasitMessageQueueConsumer fasitMessageQueueConsumer = context.getBean(FasitMessageQueueConsumer.class);

        Queue requestQueue = fasitMessageQueueConsumer.getRequestQueue(environment);
        Queue responseQueue = fasitMessageQueueConsumer.getResponseQueue(environment);

        JmsTemplate template = context.getBean(JmsTemplate.class);

        SessionCallback<TextMessage> callback = new MessageQueueSessionCallback(message, requestQueue.getName(), responseQueue.getName(), timeout);

        TextMessage response = template.execute(callback);

        if (response == null) {
            System.err.println("Did not receive a response");
            return;
        }

        String responseContent;

        try {
            responseContent = response.getText();
            System.out.println("\n Received:\n\n" + responseContent + "\n");
        } catch (JMSException exception) {
            //FIXME: Fix this
            System.err.println(exception);
        }
    }
}