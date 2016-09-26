package no.nav.tps.vedlikehold.service.command.authorisation.strategies;

/**
 * @author Øyvind Grimnes, Visma Consulting AS
 */
@FunctionalInterface
public interface AuthorisationServiceStrategy {
    boolean isAuthorised();
}
