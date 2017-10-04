package no.nav.tps.forvalteren.service.command.testdata.skd;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.service.command.testdata.FindPersonsNotInEnvironments;

@Service
public class LagreTilTps {

    private static final String NAVN_INNVANDRINGSMELDING = "Innvandring";

    @Autowired
    private SkdMessageSender skdMessageSender;

    @Autowired
    private FindPersonsNotInEnvironments findPersonsNotInEnvironments;

    @Autowired
    private CreateRelasjoner createRelasjoner;

    @Autowired
    private SkdFelterContainerTrans1 skdFelterContainerTrans1;

    public void execute(Long gruppeId, List<String> environments) {
        List<Person> personerSomIkkeEksitererITpsMiljoe = findPersonsNotInEnvironments.execute(gruppeId, environments);
        skdMessageSender.execute(NAVN_INNVANDRINGSMELDING, personerSomIkkeEksitererITpsMiljoe, environments, skdFelterContainerTrans1);
        createRelasjoner.execute(personerSomIkkeEksitererITpsMiljoe, environments);
    }

}
