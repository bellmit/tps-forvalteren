package no.nav.tps.forvalteren.service.command.authorisation.strategy;

import no.nav.tps.forvalteren.common.java.message.MessageProvider;
import no.nav.tps.forvalteren.service.user.UserContextHolder;
import no.nav.tps.forvalteren.consumer.ws.tpsws.egenansatt.EgenAnsattConsumer;
import no.nav.tps.forvalteren.domain.service.tps.authorisation.strategies.EgenAnsattServiceRutineAuthorisation;
import no.nav.tps.forvalteren.domain.service.tps.authorisation.strategies.ServiceRutineAuthorisationStrategy;
import no.nav.tps.forvalteren.service.command.exceptions.HttpUnauthorisedException;
import no.nav.tps.forvalteren.service.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DefaultEgenAnsattSecurityStrategy implements EgenAnsattSecurityStrategy {

    @Autowired
    private EgenAnsattConsumer egenAnsattConsumer;

    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private UserContextHolder userContextHolder;

    @Override
    public boolean isSupported(ServiceRutineAuthorisationStrategy a1) {
        return a1 instanceof EgenAnsattServiceRutineAuthorisation;
    }

    @Override
    public void handleUnauthorised() {
        throw new HttpUnauthorisedException(messageProvider.get("rest.service.request.exception.Unauthorized"), "api/v1/service/");
    }

    @Override
    public boolean isAuthorised(String fnr) {
        return !egenAnsattConsumer.isEgenAnsatt(fnr) || userContextHolder.getRoles().contains(UserRole.ROLE_EGEN_ANSATT_READ);
    }
}