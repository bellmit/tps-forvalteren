package no.nav.tps.forvalteren.service.command.testdata.opprett.implementation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.nav.tps.forvalteren.domain.rs.RsPersonMalRequest;
import no.nav.tps.forvalteren.service.command.testdata.opprett.FiltererUtIdenterSomAlleredeFinnesIMiljoe;
import no.nav.tps.forvalteren.service.command.testdata.opprett.Testdata;
import no.nav.tps.forvalteren.service.command.testdata.opprett.TestdataRequest;

@Service
public class DefaultTestdata implements Testdata {

    @Autowired
    private DefaultTaBortOverfloedigIdenterITestdataRequest taBortOverfloedigIdenterITestdataRequest;

    @Autowired
    private DefaultGenererIdenterForTestdataRequests genererIdenterForTestdataRequests;

    @Autowired
    private FiltererUtIdenterSomAlleredeFinnesIMiljoe filtererUtIdenterSomAlleredeFinnesIMiljoe;

    @Autowired
    private DefaultFiltrerPaaIdenterSomIkkeFinnesIDB filtrerPaaIdenterSomIkkeFinnesIDB;

    public void filtrerPaaIdenterSomIkkeFinnesIDB(List<TestdataRequest> testdataRequests) {
        filtrerPaaIdenterSomIkkeFinnesIDB.execute(testdataRequests);
    }

    public void filtererUtMiljoeUtilgjengeligeIdenterFraTestdatarequest(List<TestdataRequest> testdataRequests) {
        filtererUtIdenterSomAlleredeFinnesIMiljoe.execute(testdataRequests);
    }

    public List<TestdataRequest> genererIdenterForTestdataRequests(RsPersonMalRequest inputPersonRequest) {
        return genererIdenterForTestdataRequests.execute(inputPersonRequest);
    }

    public void taBortOverfloedigIdenterITestdataRequest(TestdataRequest request) {
        taBortOverfloedigIdenterITestdataRequest.execute(request);
    }
}
