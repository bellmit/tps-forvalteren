package no.nav.tps.vedlikehold.application.local.config;

import no.nav.tps.vedlikehold.provider.rs.config.ProviderRestConfig;
import no.nav.tps.vedlikehold.provider.web.config.ApplicationServletInitializer;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *  @author Kristian Kyvik (Visma Consulting).
 */
@Configuration
@EnableAutoConfiguration
@Import(ApplicationServletInitializer.class)
public class LocalApplicationConfig {
}
