package no.nav.tps.forvalteren.service.command.tps.servicerutiner;

import no.nav.tps.forvalteren.service.command.exceptions.HttpInternalServerErrorException;
import no.nav.tps.forvalteren.service.command.exceptions.HttpUnauthorisedException;
import no.nav.tps.forvalteren.service.command.tps.TpsRequestService;
import no.nav.tps.forvalteren.service.command.tps.servicerutiner.utils.RsTpsResponseMappingUtils;
import no.nav.tps.forvalteren.domain.service.tps.Response;
import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.definition.TpsServiceRoutineDefinition;
import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.requests.TpsRequestContext;
import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.requests.TpsServiceRoutineRequest;
import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.response.TpsServiceRoutineResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;


@Service
public class TpsRequestSender {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TpsRequestSender.class);

    @Autowired
    private FindServiceRoutineByName findServiceRoutineByName;

    @Autowired
    private RsTpsResponseMappingUtils rsTpsResponseMappingUtils;

    @Autowired
    private TpsRequestService tpsRequestService;

    public TpsServiceRoutineResponse sendTpsRequest(TpsServiceRoutineRequest request, TpsRequestContext context){
        try {
            TpsServiceRoutineDefinition serviceRoutine = findServiceRoutineByName.execute(request.getServiceRutinenavn()).get();
            Response response = tpsRequestService.executeServiceRutineRequest(request, serviceRoutine, context);
            return rsTpsResponseMappingUtils.convertToTpsServiceRutineResponse(response);
        } catch (HttpUnauthorisedException ex){
            LOGGER.error(ex.getMessage(), ex);
            throw new HttpUnauthorisedException(ex, "api/v1/service/" + request.getServiceRutinenavn());
        }catch (JMSException jmsException){
            LOGGER.error(jmsException.getMessage(), jmsException);
            throw new HttpInternalServerErrorException(jmsException, "api/v1/service");
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new HttpInternalServerErrorException(exception, "api/v1/service");
        }
    }
}
