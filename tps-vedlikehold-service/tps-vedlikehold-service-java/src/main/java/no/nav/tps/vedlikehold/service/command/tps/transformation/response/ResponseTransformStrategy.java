package no.nav.tps.vedlikehold.service.command.tps.transformation.response;

import no.nav.tps.vedlikehold.domain.service.tps.Response;
import no.nav.tps.vedlikehold.domain.service.tps.servicerutiner.transformers.Transformer;
import no.nav.tps.vedlikehold.service.command.tps.transformation.TransformStrategy;

public interface ResponseTransformStrategy extends TransformStrategy {

    void execute(Response response, Transformer transformer);

}
