package no.nav.tps.forvalteren.service.command.testdata.opprett.implementation;

import no.nav.tps.forvalteren.service.command.testdata.FiltrerPaaIdenterTilgjengeligeIMiljo;
import no.nav.tps.forvalteren.service.command.testdata.opprett.FiltererUtIdenterSomAlleredeFinnesIMiljoe;
import no.nav.tps.forvalteren.service.command.testdata.opprett.TestdataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DefaultFiltererUtIdenterSomAlleredeFinnesIMiljoe implements FiltererUtIdenterSomAlleredeFinnesIMiljoe {

    @Autowired
    private FiltrerPaaIdenterTilgjengeligeIMiljo filtrerPaaIdenterTilgjengeligeIMiljo;

    public void execute(List<TestdataRequest> testdataRequests) {
        Set<String> alleGenererteIdenter = new HashSet<>();
        for (TestdataRequest request : testdataRequests) {
            alleGenererteIdenter.addAll(request.getIdenterGenerertForKriterie());
        }
        Set<String> alleTilgjengeligIdenter = filtrerPaaIdenterTilgjengeligeIMiljo.filtrer(alleGenererteIdenter);
        taBortOpptatteIdenterRequest(testdataRequests, alleTilgjengeligIdenter);
    }

    private void taBortOpptatteIdenterRequest(List<TestdataRequest> testdataRequests, Set<String> alleTilgjengligIdenterIMiljoe) {
        for (TestdataRequest request : testdataRequests) {
            request.setIdenterTilgjengligIMiljoe(new HashSet<>(request.getIdenterGenerertForKriterie()));
            request.getIdenterTilgjengligIMiljoe().retainAll(alleTilgjengligIdenterIMiljoe);
        }
    }
}