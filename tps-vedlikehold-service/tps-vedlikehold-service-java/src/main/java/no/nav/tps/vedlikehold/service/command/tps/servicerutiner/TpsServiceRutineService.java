package no.nav.tps.vedlikehold.service.command.tps.servicerutiner;

import no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.ServiceRutineResponse;

import java.util.Map;

/**
 * @author Øyvind Grimnes, Visma Consulting AS
 */
@FunctionalInterface
public interface TpsServiceRutineService {
     ServiceRutineResponse execute(
            String requestMessage,
            String environment) throws Exception;
}
