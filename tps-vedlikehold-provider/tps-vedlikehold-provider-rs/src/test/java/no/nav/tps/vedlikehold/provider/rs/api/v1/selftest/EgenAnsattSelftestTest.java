package no.nav.tps.vedlikehold.provider.rs.api.v1.selftest;

import no.nav.tps.vedlikehold.service.command.tpsws.PingEgenAnsatt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.util.StringUtils.isEmpty;


@RunWith(MockitoJUnitRunner.class)
public class EgenAnsattSelftestTest {

    @InjectMocks
    private EgenAnsattSelftest selftest;

    @Mock
    private PingEgenAnsatt pingEgenAnsattMock;

    @Test
    public void subSystemNameIsSet() {
        String result = selftest.getSubSystemName();

        assertThat(result, is(notNullValue()));
        assertThat(isEmpty(result), is(false));
    }

    @Test
    public void callsPingCommandWhenSelftestIsPerformed() throws Exception {
        selftest.perform();

        verify(pingEgenAnsattMock).execute();
    }

    @Test
    public void returnsTrueWhenSelftestIsPerformed() throws Exception {
        boolean result = selftest.performCheck();

        assertThat(result, is(true));
    }

}
