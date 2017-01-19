package no.nav.tps.vedlikehold.provider.rs.api.v1.endpoints;

//import no.nav.tps.vedlikehold.domain.rs.testdata.RsTestDataRequest;

import com.fasterxml.jackson.databind.JsonNode;
import no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.requests.TpsRequestContext;
import no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.requests.TpsServiceRoutineRequest;
import no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.response.TpsServiceRoutineResponse;
import no.nav.tps.vedlikehold.domain.service.command.tps.testdata.Kjonn;
import no.nav.tps.vedlikehold.domain.service.command.tps.testdata.TestDataRequest;
import no.nav.tps.vedlikehold.provider.rs.api.v1.endpoints.utils.RsTpsRequestMappingUtils;
import no.nav.tps.vedlikehold.provider.rs.security.user.UserContextHolder;
import no.nav.tps.vedlikehold.service.command.testdata.FiktiveIdenterGenerator;
import no.nav.tps.vedlikehold.service.command.testdata.SkdMeldingFormatter;
import no.nav.tps.vedlikehold.service.command.tps.TpsRequestService;
import no.nav.tps.vedlikehold.service.command.tps.servicerutiner.TpsRequestSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

/**
 * Created by Rizwan Ali Ahmed, Visma Consulting AS.
 */
@RestController
@RequestMapping(value = "api/v1")
public class TestdataController {

    @Autowired
    private UserContextHolder userContextHolder;

    @Autowired
    private SkdMeldingFormatter skdMeldingFormatter;

    @Autowired
    private TpsRequestService tpsRequestService;

    @Autowired
    private RsTpsRequestMappingUtils mappingUtils;

    @Autowired
    private FiktiveIdenterGenerator fiktiveIdenterGenerator;

    @Autowired
    private TpsRequestSender tpsRequestSender;

    //TODO Husk å sette på CSFR
    @RequestMapping(value = "/testdata/skdcreate", method = RequestMethod.POST)
    public String createTestData(@RequestParam(required = false) Map<String, String> skdMeldingParameters){

        String skdMelding = skdMeldingFormatter.convertToSkdMeldingInnhold(skdMeldingParameters);

        //TODO Settes av  param etter testing. Eller body?
        TpsRequestContext context = new TpsRequestContext();
        context.setEnvironment("t4");

        try {
            tpsRequestService.executeSkdMeldingRequest(skdMelding, context );
        } catch (JMSException e) {
            e.printStackTrace();    //TODO Gjør om til logger
        }

        return skdMelding;
    }

    //TODO Vurdere om man trenger en loop for flere nummer. Vurdere mo request skal kunne innholde D-Nummer og F-nummer samtidig.. osv.
    @RequestMapping(value = "/testdata/fodsel", method = RequestMethod.GET)
    public TpsServiceRoutineResponse getFodselsnummer(@RequestParam(required = false) Map<String, Object> tpsRequestParameters){

        //TODO Tilpass når vi vet akkurat hvordan input fra Frontend kommer til å være. Hardinput av Dato f.eks er bare for testing.
        TestDataRequest testDataRequest = new TestDataRequest();
        testDataRequest.setAntallIdenter(Integer.parseInt(tpsRequestParameters.get("antall").toString()));
        testDataRequest.setIdentType("Fnr");
        testDataRequest.setKjonn(Kjonn.MANN);
        LocalDate date = LocalDate.of(1981, Month.JANUARY, 15);
        testDataRequest.setDato(date);
        List<String> fodselsnummere = fiktiveIdenterGenerator.genererFiktiveIdenter(testDataRequest);

        TpsRequestContext context = new TpsRequestContext();
        context.setUser(userContextHolder.getUser());
        context.setEnvironment(tpsRequestParameters.get("environment").toString());

        tpsRequestParameters.put("fnr", fodselsnummere);
        tpsRequestParameters.put("antallFnr", fodselsnummere.size());

        JsonNode body = mappingUtils.convert(tpsRequestParameters, JsonNode.class);

        TpsServiceRoutineRequest tpsServiceRoutineRequest = mappingUtils.convertToTpsServiceRoutineRequest(tpsRequestParameters.get("serviceRutinenavn").toString(), body);
        TpsServiceRoutineResponse tpsResponse = tpsRequestSender.sendTpsRequest(tpsServiceRoutineRequest, context);
        return tpsResponse;
    }
}
