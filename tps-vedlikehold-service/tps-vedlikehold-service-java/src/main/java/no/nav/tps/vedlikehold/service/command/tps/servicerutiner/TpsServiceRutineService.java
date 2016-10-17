package no.nav.tps.vedlikehold.service.command.tps.servicerutiner;

import no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.requests.TpsRequestServiceRoutine;

/**
 * @author Øyvind Grimnes, Visma Consulting AS
 */
@FunctionalInterface
public interface TpsServiceRutineService {
     String execute(TpsRequestServiceRoutine request) throws Exception;
}
