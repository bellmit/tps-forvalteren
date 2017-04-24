package no.nav.tps.forvalteren.provider.web.config;

import no.nav.tps.forvalteren.provider.rs.security.logging.MDCInterceptor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableAutoConfiguration
@Import(ApplicationConfig.class)
public class ApplicationServletInitializer extends SpringBootServletInitializer {
    /**
     * Configures the default Spring Boot servlet &quot;dispatcherServlet&quot; to expose resources on /internal.
     */
    @Bean
    WebMvcConfigurerAdapter dispatcherServletConfigurer(final MDCInterceptor mdcInterceptor) {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/internal/*");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(mdcInterceptor);
            }
        };
    }
}