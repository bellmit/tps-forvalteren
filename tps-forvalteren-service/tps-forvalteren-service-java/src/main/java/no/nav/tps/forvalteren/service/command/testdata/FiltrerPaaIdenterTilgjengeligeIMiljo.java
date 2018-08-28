package no.nav.tps.forvalteren.service.command.testdata;

import static no.nav.tps.forvalteren.service.command.testdata.utils.ExtractDataFromTpsServiceRoutineResponse.trekkUtIdenterMedStatusIkkeFunnetFraResponse;
import static no.nav.tps.forvalteren.service.command.testdata.utils.TpsRequestParameterCreator.opprettParametereForM201TpsRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import no.nav.tps.forvalteren.domain.service.tps.ResponseStatus;
import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.requests.TpsRequestContext;
import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.requests.TpsServiceRoutineRequest;
import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.response.TpsServiceRoutineResponse;
import no.nav.tps.forvalteren.service.command.FilterEnvironmentsOnDeployedEnvironment;
import no.nav.tps.forvalteren.service.command.exceptions.TpsfTechnicalException;
import no.nav.tps.forvalteren.service.command.tps.servicerutiner.TpsRequestSender;
import no.nav.tps.forvalteren.service.command.tps.servicerutiner.utils.RsTpsRequestMappingUtils;
import no.nav.tps.forvalteren.service.user.UserContextHolder;

@Slf4j
@Service
public class FiltrerPaaIdenterTilgjengeligeIMiljo {
    
    private static final int MAX_ANTALL_IDENTER_TIL_REQUEST_M201 = 80;
    
    private static final String TPS_SYSTEM_ERROR_CODE = "12";
    
    @Autowired
    private UserContextHolder userContextHolder;
    
    @Autowired
    private TpsRequestSender tpsRequestSender;
    
    @Autowired
    private RsTpsRequestMappingUtils mappingUtils;
    
    @Autowired
    private FilterEnvironmentsOnDeployedEnvironment filterEnvironmentsOnDeployedEnvironment;
    
    public Set<String> filtrer(Collection<String> identer, Set<String> environments) {
        if (identer.size() <= MAX_ANTALL_IDENTER_TIL_REQUEST_M201) {
            return filtrerPaaIdenter(identer, environments);
            
        } else {
            Set<String> tilgjengeligeIdenter = new HashSet<>();
            List<String> identerListe = new ArrayList<>(identer);
            int batchStart = 0;
            while (batchStart < identer.size()) {
                tilgjengeligeIdenter.addAll(hentEnBatchTilgjengeligeIdenter(batchStart, identerListe, environments));
                batchStart = batchStart + MAX_ANTALL_IDENTER_TIL_REQUEST_M201;
            }
            return tilgjengeligeIdenter;
        }
    }
    
    private Set<String> hentEnBatchTilgjengeligeIdenter(int batchStart, List<String> identer, Set<String> environments) {
        int batchStop = (identer.size() <= batchStart + MAX_ANTALL_IDENTER_TIL_REQUEST_M201)
                ? identer.size() : (batchStart + MAX_ANTALL_IDENTER_TIL_REQUEST_M201);
        
        return filtrerPaaIdenter(identer.subList(batchStart, batchStop), environments);
    }
    
    private Set<String> filtrerPaaIdenter(Collection<String> identer, Set<String> environments) {
        
        Map<String, Object> tpsRequestParameters = opprettParametereForM201TpsRequest(identer, "A0");
        
        TpsRequestContext context = new TpsRequestContext();
        context.setUser(userContextHolder.getUser());
        
        return hentIdenterSomErTilgjengeligeIAlleMiljoer(tpsRequestParameters, context, environments);
    }
    
    private Set<String> hentIdenterSomErTilgjengeligeIAlleMiljoer(Map<String, Object> tpsRequestParameters, TpsRequestContext context, Set<String> environments) {
        Set<String> tilgjengeligeIdenterAlleMiljoer = new HashSet<>((Collection<String>) tpsRequestParameters.get("fnr"));
        
        Set<String> environmentsToCheck = filterEnvironmentsOnDeployedEnvironment.execute(environments);
        
        filtrerOgTaVarePaaIdenterTilgjengeligIMiljoer(tilgjengeligeIdenterAlleMiljoer, environmentsToCheck, tpsRequestParameters, context);
        
        return tilgjengeligeIdenterAlleMiljoer;
    }
    
    private void filtrerOgTaVarePaaIdenterTilgjengeligIMiljoer(Set<String> tilgjengligIdenter, Set<String> miljoerAaSjekke, Map<String, Object> tpsRequestParameters, TpsRequestContext context) {
        for (String miljoe : miljoerAaSjekke) {
            context.setEnvironment(miljoe);
            
            TpsServiceRoutineRequest tpsServiceRoutineRequest = mappingUtils.convertToTpsServiceRoutineRequest(String.valueOf(tpsRequestParameters
                    .get("serviceRutinenavn")), tpsRequestParameters);
            TpsServiceRoutineResponse tpsResponse = tpsRequestSender.sendTpsRequest(tpsServiceRoutineRequest, context);
            
            checkForTpsSystemfeil(tpsResponse);
            
            Set<String> tilgjengeligeIdenterFraEtBestemtMiljoe = trekkUtIdenterMedStatusIkkeFunnetFraResponse(tpsResponse);
            
            tilgjengligIdenter.retainAll(tilgjengeligeIdenterFraEtBestemtMiljoe);
        }
    }
    
    private void checkForTpsSystemfeil(TpsServiceRoutineResponse response) {
        if (response.getXml().isEmpty()) {
            throw new TpsfTechnicalException("TPS returnerte tom streng");
        }
        LinkedHashMap rep = (LinkedHashMap) response.getResponse();
        ResponseStatus status = (ResponseStatus) rep.get("status");
        
        if (TPS_SYSTEM_ERROR_CODE.equals(status.getKode())) {
            log.error("TPS returnerte SYSTEM ERROR");
            throw new TpsfTechnicalException("TPS returnerte SYSTEM ERROR");
        }
    }
}
