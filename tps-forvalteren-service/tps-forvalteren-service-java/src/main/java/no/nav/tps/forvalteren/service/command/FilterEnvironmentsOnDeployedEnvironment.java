package no.nav.tps.forvalteren.service.command;

import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FilterEnvironmentsOnDeployedEnvironment {

    @Value("${fasit.environment.name}")
    private String deployedEnvironment;

    public Set<String> execute(Set<String> environments) {
        char env = deployedEnvironment.charAt(0);
        switch (env) {
        case 'u':
            return EnvironmentsFilter.create()
                    .include("u*")
                    .include("t*")
                    .filter(environments);
        case 't':
        case 'q':
            return EnvironmentsFilter.create()
                    .include("u*")
                    .include("t*")
                    .include("q*")
                    .filter(environments);
        case 'p':
            return EnvironmentsFilter.create()
                    .include("p*")
                    .filter(environments);
        default:
            return EnvironmentsFilter.create()
                    .include("u*")
                    .filter(environments);
        }
    }
}
