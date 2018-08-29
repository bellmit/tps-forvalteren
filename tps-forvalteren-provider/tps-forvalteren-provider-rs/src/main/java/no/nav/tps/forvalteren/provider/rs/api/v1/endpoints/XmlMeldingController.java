package no.nav.tps.forvalteren.provider.rs.api.v1.endpoints;

import static no.nav.tps.forvalteren.provider.rs.config.ProviderConstants.OPERATION;
import static no.nav.tps.forvalteren.provider.rs.config.ProviderConstants.RESTSERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import no.nav.freg.metrics.annotations.Metrics;
import no.nav.freg.spring.boot.starters.log.exceptions.LogExceptions;
import no.nav.tps.forvalteren.domain.rs.RsPureXmlMessageResponse;
import no.nav.tps.forvalteren.domain.rs.RsTpsMelding;
import no.nav.tps.forvalteren.provider.rs.security.logging.BaseProvider;
import no.nav.tps.forvalteren.service.command.tps.xmlmelding.TpsXmlSender;

@RestController
@RequestMapping(value = "api/v1")
@PreAuthorize("hasRole({'ROLE_TPSF_SERVICERUTINER','ROLE_TPSF_SKDMELDING'})")
public class XmlMeldingController extends BaseProvider {

    private static final String REST_SERVICE_NAME = "xmlMelding";
    
    @Autowired
    private TpsXmlSender tpsXmlSender;

    @LogExceptions
    @Metrics(value = "provider", tags = { @Metrics.Tag(key = RESTSERVICE, value = REST_SERVICE_NAME), @Metrics.Tag(key = OPERATION, value = "sendXmlMelding") })
    @RequestMapping(value = "/xmlmelding", method = RequestMethod.POST)
    @ConditionalOnProperty(prefix = "tps.forvalteren", name = "production-mode", havingValue = "false")
    public RsPureXmlMessageResponse sendXmlMelding(@RequestBody RsTpsMelding rsTpsMelding) throws Exception {

        RsPureXmlMessageResponse response = new RsPureXmlMessageResponse();
        response.setXml(tpsXmlSender.sendTpsMelding(rsTpsMelding));
        return response;
    }
}