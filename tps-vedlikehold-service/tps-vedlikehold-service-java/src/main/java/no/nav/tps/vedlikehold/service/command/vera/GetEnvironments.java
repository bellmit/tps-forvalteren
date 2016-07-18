package no.nav.tps.vedlikehold.service.command.vera;

import no.nav.tps.vedlikehold.consumer.rs.vera.VeraConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Tobias Hansen (Visma Consulting AS).
 */
@Service
public class GetEnvironments {

    @Autowired
    VeraConsumer veraConsumer;

    public Set<String> execute(String application){
        return veraConsumer.getEnvironments(application);
    }
}
