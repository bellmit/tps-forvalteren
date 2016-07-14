package no.nav.tps.vedlikehold.web.model;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import no.nav.tps.vedlikehold.provider.web.model.AggregateSelftestResult;
import no.nav.tps.vedlikehold.provider.web.model.SelftestResult;

import static no.nav.tps.vedlikehold.provider.web.model.SelftestResult.Status.FEILET;
import static no.nav.tps.vedlikehold.provider.web.model.SelftestResult.Status.OK;

/**
 * @author Kristian Kyvik (Visma Consulting AS).
 */
public class AggregateSelftestResultTest {

    @Test
    public void returnsFeiletIfAtLeastOneSubSystemHasStatusFeilet() {
        SelftestResult subSystemResult1 = new SelftestResult("Web service 1");
        SelftestResult subSystemResult2 = new SelftestResult("Web service 2", "Web service isn't responding");
        SelftestResult subSystemResult3 = new SelftestResult("Database");

        AggregateSelftestResult aggregateResult = new AggregateSelftestResult();
        aggregateResult.addResults(asList(subSystemResult1, subSystemResult2, subSystemResult3));

        SelftestResult.Status aggregateStatus = aggregateResult.getStatus();

        assertThat(aggregateStatus, is(FEILET));
    }

    @Test
    public void returnsOkIfAllSubSystemsHaveStatusOk() {
        SelftestResult subSystemResult1 = new SelftestResult("Web service 1");
        SelftestResult subSystemResult2 = new SelftestResult("Web service 2");
        SelftestResult subSystemResult3 = new SelftestResult("Database");

        AggregateSelftestResult aggregateResult = new AggregateSelftestResult();
        aggregateResult.addResults(asList(subSystemResult1, subSystemResult2, subSystemResult3));

        SelftestResult.Status aggregateStatus = aggregateResult.getStatus();

        assertThat(aggregateStatus, is(OK));
    }

    @Test
    public void returnsOkNoSubSystemsHaveBeenAdded() {
        AggregateSelftestResult aggregateResult = new AggregateSelftestResult();

        SelftestResult.Status aggregateStatus = aggregateResult.getStatus();

        assertThat(aggregateStatus, is(OK));
    }
}
