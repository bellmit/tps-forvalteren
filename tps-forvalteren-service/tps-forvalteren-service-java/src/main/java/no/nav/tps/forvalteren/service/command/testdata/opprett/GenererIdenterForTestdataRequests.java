package no.nav.tps.forvalteren.service.command.testdata.opprett;

import no.nav.tps.forvalteren.domain.rs.RsPersonKriteriumRequest;

import java.util.List;

@FunctionalInterface
public interface GenererIdenterForTestdataRequests {

    List<TestdataRequest> execute(RsPersonKriteriumRequest personKriterierRequest);

}
