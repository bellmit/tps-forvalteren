package no.nav.tps.forvalteren.service.command.testdata.opprett.implementation;

import no.nav.tps.forvalteren.domain.rs.RsPersonKriteriumRequest;
import no.nav.tps.forvalteren.domain.rs.RsPersonKriterier;
import no.nav.tps.forvalteren.domain.rs.RsPersonMal;
import no.nav.tps.forvalteren.domain.rs.RsPersonMalRequest;
import no.nav.tps.forvalteren.service.command.testdata.FiktiveIdenterGenerator;
import no.nav.tps.forvalteren.service.command.testdata.opprett.TestdataRequest;
import no.nav.tps.forvalteren.service.command.testdata.opprett.GenererIdenterForTestdataRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DefaultGenererIdenterForTestdataRequests implements GenererIdenterForTestdataRequests {

    @Autowired
    private FiktiveIdenterGenerator fiktiveIdenterGenerator;

    public List<TestdataRequest> execute(RsPersonMalRequest inputPersonRequest) {
        List<TestdataRequest> requests = new ArrayList<>();

        inputPersonRequest.getInputPersonMalRequest().stream().forEach(RsPersonMal -> {
            TestdataRequest request = new TestdataRequest(RsPersonMal);
            request.setIdenterGenerertForKriterie(fiktiveIdenterGenerator.genererFiktiveIdenter(RsPersonMal));
            taBortIdenterLagtTilIAndreKriterier(requests, request.getIdenterGenerertForKriterie());
            requests.add(request);
        });


        return requests;
    }

    private void taBortIdenterLagtTilIAndreKriterier(List<TestdataRequest> testdataRequests, Set<String> identerForKritere) {
        for (String ident : new ArrayList<>(identerForKritere)) {
            for (TestdataRequest testdataRequest : testdataRequests) {
                if (testdataRequest.getIdenterGenerertForKriterie().contains(ident)) {
                    identerForKritere.remove(ident);
                }
            }
        }
    }
}

