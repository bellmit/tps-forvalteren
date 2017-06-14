package no.nav.tps.forvalteren.service.command.testdata;

import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.requests.TpsRequestContext;
import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.requests.TpsServiceRoutineRequest;
import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.response.TpsServiceRoutineResponse;
import no.nav.tps.forvalteren.service.command.tps.servicerutiner.TpsRequestSender;
import no.nav.tps.forvalteren.service.command.tps.servicerutiner.utils.RsTpsRequestMappingUtils;
import no.nav.tps.forvalteren.service.user.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class FilterPaaIdenterTilgjengeligeIMiljo {

    private static final int HIGHEST_T_ENVIRONMENT = 13;
    private static final int LOWEST_T_ENVIRONMENT = 0;
    private static final int MAX_ANTALL_IDENTER_TIL_REQUEST_M201 = 80;

    @Autowired
    private UserContextHolder userContextHolder;

    @Autowired
    private TpsRequestSender tpsRequestSender;

    @Autowired
    private RsTpsRequestMappingUtils mappingUtils;

    public Set<String> filtrer(Collection<String> identer){
        if(!(identer.size() > MAX_ANTALL_IDENTER_TIL_REQUEST_M201)){

            return filtrerPaaIdenter(identer);

        } else {

            Set<String> tilgjengeligeIdenter = new HashSet<>();
            List<String> identerListe = new ArrayList<>(identer);
            int batchStart = 0;
            int batchStop;
            while(batchStart < identer.size()){
                batchStop = (identer.size() <= batchStart+MAX_ANTALL_IDENTER_TIL_REQUEST_M201)
                            ? identer.size() : (batchStart+MAX_ANTALL_IDENTER_TIL_REQUEST_M201);

                Set<String> tilgjengeligeIdenterFraEnJobb = filtrerPaaIdenter(identerListe.subList(batchStart, batchStop));
                tilgjengeligeIdenter.addAll(tilgjengeligeIdenterFraEnJobb);
                batchStart = batchStart + MAX_ANTALL_IDENTER_TIL_REQUEST_M201;
            }
            return tilgjengeligeIdenter;
        }
    }

    private Set<String> filtrerPaaIdenter(Collection<String> identer){

        Map<String, Object> tpsRequestParameters = opprettParametereForM201TpsRequest(identer);

        TpsRequestContext context = new TpsRequestContext();
        context.setUser(userContextHolder.getUser());

        return hentIdenterSomErTilgjengeligeIAlleMiljoer(tpsRequestParameters, context);
    }

    private Set<String> hentIdenterSomErTilgjengeligeIAlleMiljoer(Map<String, Object> tpsRequestParameters, TpsRequestContext context){

        Set<String> tilgjengeligeIdenterAlleMiljoer = new HashSet<>();

        for(int i=LOWEST_T_ENVIRONMENT; i<HIGHEST_T_ENVIRONMENT; i++){
            if(i == 7){
                continue;       // The queue manager channel 'T7_TPSWS' for this env does not exist.
            }
            context.setEnvironment("t"+i);

            TpsServiceRoutineRequest tpsServiceRoutineRequest = mappingUtils.convertToTpsServiceRoutineRequest(String.valueOf(tpsRequestParameters.get("serviceRutinenavn")), tpsRequestParameters);
            TpsServiceRoutineResponse tpsResponse = tpsRequestSender.sendTpsRequest(tpsServiceRoutineRequest, context);

            if(kunneIkkeLeggeMeldingPaaKoe(tpsResponse)){
                continue;
            }

            Set<String> tilgjengeligeIdenterFraEtBestemtMiljoe = trekkUtIdenterFraResponse(tpsResponse);

            if(i == 0){
                tilgjengeligeIdenterAlleMiljoer.addAll(tilgjengeligeIdenterFraEtBestemtMiljoe);
            } else {
                tilgjengeligeIdenterAlleMiljoer.retainAll(tilgjengeligeIdenterFraEtBestemtMiljoe);
            }
        }
        return tilgjengeligeIdenterAlleMiljoer;
    }

    private boolean kunneIkkeLeggeMeldingPaaKoe(TpsServiceRoutineResponse response){
        return response.getXml().isEmpty();
    }

    private Map<String,Object> opprettParametereForM201TpsRequest(Collection<String> identer){
        Map<String, Object> tpsRequestParameters = new HashMap<>();
        tpsRequestParameters.put("serviceRutinenavn","FS03-FDLISTER-DISKNAVN-M");
        tpsRequestParameters.put("fnr", identer);
        tpsRequestParameters.put("antallFnr", identer.size());
        tpsRequestParameters.put("aksjonsKode","A0");
        return tpsRequestParameters;
    }

    private Set<String> trekkUtIdenterFraResponse(TpsServiceRoutineResponse tpsResponse){
        LinkedHashMap responseMap = (LinkedHashMap)tpsResponse.getResponse();
        Set<String> tilgjengeligeIdenter = new HashSet<>();

        int antallIdenter = (int) responseMap.get("antallTotalt");

        for(int i = 1; i< antallIdenter+1; i++){
            LinkedHashMap data = (LinkedHashMap) responseMap.get("data"+i);
            tilgjengeligeIdenter.add(String.valueOf(data.get("fnr")));
        }

        return tilgjengeligeIdenter;
    }
}
