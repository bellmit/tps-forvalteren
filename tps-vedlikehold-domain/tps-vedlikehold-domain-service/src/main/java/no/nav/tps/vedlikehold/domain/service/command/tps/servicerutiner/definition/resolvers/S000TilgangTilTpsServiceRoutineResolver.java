package no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.definition.resolvers;


import no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.definition.TpsServiceRoutineDefinition;
import no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.requests.hent.TpsPingServiceRoutineRequest;

import static no.nav.tps.vedlikehold.domain.service.command.tps.authorisation.strategies.DiskresjonskodeAuthorisation.diskresjonskodeAuthorisation;
import static no.nav.tps.vedlikehold.domain.service.command.tps.authorisation.strategies.EgenAnsattAuthorisation.egenAnsattAuthorisation;
import static no.nav.tps.vedlikehold.domain.service.command.tps.config.TpsConstants.REQUEST_QUEUE_SERVICE_RUTINE_ALIAS;
import static no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.definition.TpsServiceRoutineDefinitionBuilder.aTpsServiceRoutine;
import static no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.transformers.request.ServiceRoutineRequestTransform.serviceRoutineXmlWrappingAppender;
import static no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.transformers.response.ResponseStatusTransformer.extractStatusFromXmlElement;

/**
 * @author Kenneth Gunnerud (Visma Consulting AS).
 */
public class S000TilgangTilTpsServiceRoutineResolver implements ServiceRoutineResolver {

    @Override
    public TpsServiceRoutineDefinition resolve() {
        return aTpsServiceRoutine()
                .name("FS03-OTILGANG-TILSRTPS-O")
                .internalName("S000 Tilgang til Tps")
                .javaClass(TpsPingServiceRoutineRequest.class)
                .config()
                    .requestQueue(REQUEST_QUEUE_SERVICE_RUTINE_ALIAS)
                .and()
                .transformer()
                    .preSend(serviceRoutineXmlWrappingAppender())
                    .postSend(extractStatusFromXmlElement("svarStatus"))
                .and()

                .securityBuilder()
                    .addRequiredSearchAuthorisationStrategy(diskresjonskodeAuthorisation())
                    .addRequiredSearchAuthorisationStrategy(egenAnsattAuthorisation())
                    .addSecurity()

                .build();
    }
}
