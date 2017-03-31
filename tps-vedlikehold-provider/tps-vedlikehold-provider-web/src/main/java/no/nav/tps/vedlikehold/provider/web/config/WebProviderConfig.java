package no.nav.tps.vedlikehold.provider.web.config;

import no.nav.tps.vedlikehold.common.java.config.CommonConfig;
import no.nav.tps.vedlikehold.provider.rs.config.RestProviderConfig;
import no.nav.tps.vedlikehold.provider.rs.api.v1.selftest.Selftest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;


@Configuration
@EnableConfigurationProperties
@PropertySource("classpath:global.properties")
@Import({RestProviderConfig.class,
        CommonConfig.class,
})
@ComponentScan(basePackageClasses = {
        Selftest.class
})
public class WebProviderConfig {

}
