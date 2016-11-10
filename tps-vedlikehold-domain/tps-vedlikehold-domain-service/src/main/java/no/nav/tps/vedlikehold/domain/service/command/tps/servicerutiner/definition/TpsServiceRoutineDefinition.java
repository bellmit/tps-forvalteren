package no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.definition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.nav.tps.vedlikehold.domain.service.command.tps.TpsParameter;
import no.nav.tps.vedlikehold.domain.service.command.tps.authorisation.strategies.AuthorisationStrategy;
import no.nav.tps.vedlikehold.domain.service.command.tps.config.TpsRequestConfig;
import no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.transformers.Transformer;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;
import java.util.Set;

/**
 * @author Øyvind Grimnes, Visma Consulting AS
 */
@Getter
@Setter
@NoArgsConstructor
public class TpsServiceRoutineDefinition {

    private String name;
    private String internalName;    // (DisplayName)

    @JsonIgnore
    private Class<?> javaClass;

    @JsonIgnore
    private TpsRequestConfig config;

    private List<TpsParameter> parameters;

    @JsonIgnore
    private List<Transformer> transformers;

    private List<AuthorisationStrategy> securityServiceStrategies;

    private Set<String> requiredRoles;
}
