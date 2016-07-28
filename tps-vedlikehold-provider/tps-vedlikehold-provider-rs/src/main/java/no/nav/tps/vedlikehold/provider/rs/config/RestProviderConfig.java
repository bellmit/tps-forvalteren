package no.nav.tps.vedlikehold.provider.rs.config;

import no.nav.tps.vedlikehold.provider.rs.api.v1.documentation.SwaggerConfig;
import no.nav.tps.vedlikehold.provider.rs.api.v1.endpoints.UserController;
import no.nav.tps.vedlikehold.provider.rs.api.v1.exceptions.HttpExceptionAdvice;
import no.nav.tps.vedlikehold.provider.rs.security.config.RestSecurityConfig;
import no.nav.tps.vedlikehold.provider.rs.security.config.WebSecurityConfig;
import no.nav.tps.vedlikehold.service.config.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Øyvind Grimnes, Visma Consulting AS
 */

@Configuration
@Import({
        ServiceConfig.class,
        SwaggerConfig.class,
        WebSecurityConfig.class,
        RestSecurityConfig.class,
})
@ComponentScan(basePackageClasses = {
        UserController.class,
        HttpExceptionAdvice.class
})
public class RestProviderConfig {
}
