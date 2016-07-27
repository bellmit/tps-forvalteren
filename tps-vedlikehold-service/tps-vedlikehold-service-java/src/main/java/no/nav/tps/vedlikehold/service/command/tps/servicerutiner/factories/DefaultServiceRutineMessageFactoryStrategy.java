package no.nav.tps.vedlikehold.service.command.tps.servicerutiner.factories;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author Øyvind Grimnes, Visma Consulting AS
 */
public class DefaultServiceRutineMessageFactoryStrategy implements ServiceRutineMessageFactoryStrategy {

    private Map<String, Object> parameters;

    public DefaultServiceRutineMessageFactoryStrategy(String serviceRutineName, Map<String, Object> parameters) {
        this.parameters = new HashMap<>(parameters);

        this.parameters.put("serviceRutinenavn", serviceRutineName);

        this.parameters.remove("environment");

        String aksjonskode = (String) this.parameters.get("aksjonsKode");

        if (!isEmpty(aksjonskode)) {
            this.parameters.put("aksjonsKode", aksjonskode.substring(0,1));
            this.parameters.put("aksjonsKode2", aksjonskode.substring(1));
        }
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }
}
