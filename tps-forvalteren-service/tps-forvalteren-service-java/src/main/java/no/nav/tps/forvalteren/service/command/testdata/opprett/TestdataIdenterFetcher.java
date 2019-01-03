package no.nav.tps.forvalteren.service.command.testdata.opprett;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.nav.tps.forvalteren.common.java.message.MessageProvider;
import no.nav.tps.forvalteren.domain.rs.RsPersonKriteriumRequest;
import no.nav.tps.forvalteren.service.command.exceptions.HttpCantSatisfyRequestException;

@Service
public class TestdataIdenterFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestdataIdenterFetcher.class);
    private static final int MAX_TRIES = 40;
    private static final String PRODLIKE_ENV = "q0";

    @Autowired
    private FiltererUtIdenterSomAlleredeFinnesIMiljoe filtererUtIdenterSomAlleredeFinnesIMiljoe;

    @Autowired
    private FiltrerPaaIdenterSomIkkeFinnesIDB filtrerPaaIdenterSomIkkeFinnesIDB;

    @Autowired
    private TaBortOverfloedigIdenterITestdataRequest taBortOverfloedigIdenterITestdataRequest;

    @Autowired
    private GenererIdenterForTestdataRequests genererIdenterForTestdataRequests;

    @Autowired
    private MessageProvider messageProvider;

    public List<TestdataRequest> getTestdataRequestsInnholdeneTilgjengeligeIdenter(RsPersonKriteriumRequest personKriterierListe) {
        List<TestdataRequest> testdataRequests = lagTestdatarequester(personKriterierListe, null);

        if (!erAlleKriteriaOppfylt(testdataRequests)) {
            oppdaterTestdataRequestsMedIdenterTilManglendeKriterier(testdataRequests, false, null);
        }

        return testdataRequests;
    }

    public List<TestdataRequest> getTestdataRequestsInnholdeneTilgjengeligeIdenterFlereMiljoer(RsPersonKriteriumRequest personKriterierListe, Set<String> miljoer) {
        List<TestdataRequest> testdataRequests = lagTestdatarequester(personKriterierListe, miljoer);

        if (!erAlleKriteriaOppfylt(testdataRequests)) {
            oppdaterTestdataRequestsMedIdenterTilManglendeKriterier(testdataRequests, true, miljoer);
        }

        return testdataRequests;
    }

    private List<TestdataRequest> lagTestdatarequester(RsPersonKriteriumRequest personKriterierListe, Set<String> environments){
        List<TestdataRequest> testdataRequests = genererIdenterForTestdataRequests.execute(personKriterierListe);

        if (environments != null) {
            environments.add(PRODLIKE_ENV);
        }
        filtererUtIdenterSomAlleredeFinnesIMiljoe.executeMotAlleMiljoer(testdataRequests, environments);

        filtrerPaaIdenterSomIkkeFinnesIDB.execute(testdataRequests);

        taBortOverflodigeIdenterFraTestRequests(testdataRequests);

        return testdataRequests;
    }

    private void oppdaterTestdataRequestsMedIdenterTilManglendeKriterier(List<TestdataRequest> testdataRequests, boolean alleMiljoer, Set<String> miljoer) {
        for (TestdataRequest request : testdataRequests) {
            if (!harNokIdenterForKritereIRequest(request)) {
                int counter = 0;
                while ((counter < MAX_TRIES) && !harNokIdenterForKritereIRequest(request)) {
                    RsPersonKriteriumRequest singelKriterieListe = new RsPersonKriteriumRequest();
                    singelKriterieListe.setPersonKriterierListe(new ArrayList<>());
                    singelKriterieListe.getPersonKriterierListe().add(request.getKriterium());

                    List<TestdataRequest> testdataRequestSingleList = genererIdenterForTestdataRequests.execute(singelKriterieListe);

                    if(alleMiljoer){
                        miljoer.add(PRODLIKE_ENV);
                        filtererUtIdenterSomAlleredeFinnesIMiljoe.executeMotAlleMiljoer(testdataRequestSingleList, miljoer);
                    } else {
                        filtererUtIdenterSomAlleredeFinnesIMiljoe.executeMotProdliktMiljoe(testdataRequestSingleList);
                    }

                    filtrerPaaIdenterSomIkkeFinnesIDB.execute(testdataRequestSingleList);

                    taBortOverflodigeIdenterFraTestRequests(testdataRequestSingleList);

                    request.getIdenterTilgjengligIMiljoe().addAll(testdataRequestSingleList.get(0).getIdenterTilgjengligIMiljoe());

                    taBortOverflodigeIdenterFraTestRequest(request);

                    counter++;
                }
                if (counter == MAX_TRIES) {
                    HttpCantSatisfyRequestException exception = new HttpCantSatisfyRequestException(messageProvider.get("rest.service.request.exception.Unsatisfied"), "api/v1/testdata/");
                    LOGGER.error(exception.getMessage(), exception);
                    throw exception;
                }
            }
        }
    }

    private void taBortOverflodigeIdenterFraTestRequest(TestdataRequest request) {
        taBortOverfloedigIdenterITestdataRequest.execute(request);
    }

    private void taBortOverflodigeIdenterFraTestRequests(List<TestdataRequest> requests) {
        for (TestdataRequest request : requests) {
            taBortOverfloedigIdenterITestdataRequest.execute(request);
        }
    }

    private static boolean erAlleKriteriaOppfylt(List<TestdataRequest> testdataRequests) {
        for (TestdataRequest request : testdataRequests) {
            if (!harNokIdenterForKritereIRequest(request)) {
                return false;
            }
        }
        return true;
    }

    private static boolean harNokIdenterForKritereIRequest(TestdataRequest request) {
        return request.getIdenterTilgjengligIMiljoe().size() >= request.getKriterium().getAntall();
    }
}
