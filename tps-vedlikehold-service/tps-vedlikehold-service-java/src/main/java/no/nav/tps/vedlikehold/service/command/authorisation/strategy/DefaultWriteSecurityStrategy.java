package no.nav.tps.vedlikehold.service.command.authorisation.strategy;

import no.nav.tps.vedlikehold.common.java.message.MessageProvider;
import no.nav.tps.vedlikehold.domain.service.command.tps.authorisation.strategies.AuthorisationStrategy;
import no.nav.tps.vedlikehold.domain.service.command.tps.authorisation.strategies.WriteAuthorsationStrategy;
import no.nav.tps.vedlikehold.service.command.authorisation.RolesService;
import no.nav.tps.vedlikehold.service.command.exceptions.HttpUnauthorisedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created by F148888 on 10.11.2016.
 */
public class DefaultWriteSecurityStrategy implements WriteSecurityStrategy{

    @Autowired
    private RolesService rolesService;

    @Autowired
    private MessageProvider messageProvider;

    @Override
    public boolean isSupported(AuthorisationStrategy a1) {
        return a1 instanceof WriteAuthorsationStrategy;
    }

    @Override
    public void authorise(Set<String> userRoles, String environment) {
        Set<String> rolesRequiredForEnvironment = rolesService.getRolesForEnvironment(environment, RolesService.RoleType.WRITE);

        // Retain all roles present in both authorised roles, and the users roles /
        userRoles.retainAll(rolesRequiredForEnvironment);
        if(userRoles.isEmpty()){
            throw new HttpUnauthorisedException(messageProvider.get("rest.service.request.exception.Unauthorized"), "api/v1/service/");
        }
    }
}
