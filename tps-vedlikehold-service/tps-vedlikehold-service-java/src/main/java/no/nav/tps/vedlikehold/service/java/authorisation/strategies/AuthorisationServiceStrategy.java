package no.nav.tps.vedlikehold.service.java.authorisation.strategies;

import no.nav.tps.vedlikehold.domain.service.User;

/**
 * @author Øyvind Grimnes, Visma Consulting AS
 */
public interface AuthorisationServiceStrategy {
    Boolean userIsAuthorisedToReadPerson(User user, String fnr);
}
