package no.nav.tps.vedlikehold.provider.rs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configure automated swagger API documentation
 *
 * @author Øyvind Grimnes, Visma Consulting AS
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/v1/.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "TPS Maintenance Client",
                "Awesome summer intern project",
                "0.1",
                "https://our.tos.com",
                new Contact("Pusekatt", "stash.devillo.no", "oyvind.grimnes@nav.no"),
                "MIT Licence",
                "https://opensource.org/licenses/MIT"
        );
    }
}
