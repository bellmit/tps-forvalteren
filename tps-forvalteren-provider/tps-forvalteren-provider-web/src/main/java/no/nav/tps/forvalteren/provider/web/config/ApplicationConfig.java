package no.nav.tps.forvalteren.provider.web.config;

import no.nav.tps.forvalteren.provider.rs.config.MetricsConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        WebProviderConfig.class,
        MetricsConfig.class
})
public class ApplicationConfig {
}