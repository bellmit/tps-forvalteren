package no.nav.tps.vedlikehold.service.command.authorisation.strategy;

import no.nav.tps.vedlikehold.common.java.message.MessageProvider;
import no.nav.tps.vedlikehold.domain.service.command.tps.Request;
import no.nav.tps.vedlikehold.domain.service.command.tps.authorisation.strategies.AuthorisationStrategy;
import no.nav.tps.vedlikehold.domain.service.command.tps.authorisation.strategies.ReadAuthorisationStrategy;
import no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.requests.TpsRequestContext;
import no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.requests.TpsServiceRoutineRequest;
import no.nav.tps.vedlikehold.service.command.authorisation.RolesService;
import no.nav.tps.vedlikehold.service.command.exceptions.HttpUnauthorisedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContext;

import java.util.Set;

@Component
public class DefaultReadSecurityStrategy implements ReadSecurityStrategy {

    @Autowired
    private RolesService rolesService;

    @Autowired
    private MessageProvider messageProvider;

    @Override
    public boolean isSupported(AuthorisationStrategy a1) {
        return a1 instanceof ReadAuthorisationStrategy;
    }

    @Override
    public void authorise(Set<String> userRoles, TpsRequestContext request) {
        String environment = request.getEnvironment();
        Set<String> rolesRequiredForEnvironment = rolesService.getRolesForEnvironment(environment, RolesService.RoleType.READ);
        userRoles.add("${tps.vedlikehold.securityBuilder.t.readroles}");    //TODO Hack for å få den til å være authorisert uten riktig role.

        // Retain all roles present in both authorised roles, and the users roles /
        userRoles.retainAll(rolesRequiredForEnvironment);
        if(userRoles.isEmpty()){
            throw new HttpUnauthorisedException(messageProvider.get("rest.service.request.exception.Unauthorized"), "api/v1/service/");
        }
    }
}
