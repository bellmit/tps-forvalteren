package no.nav.tps.forvalteren.service.command.testdata;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;

import no.nav.tps.forvalteren.domain.jpa.Doedsmelding;
import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.repository.jpa.DoedsmeldingRepository;

@Service
public class SaveDoedsmeldingToDB {

    private static final Boolean MELDING_SENT_CONFIRMED = true;

    @Autowired
    private DoedsmeldingRepository doedsmeldingRepository;

    public void execute(List<Person> personer) {
        List<Doedsmelding> doedsmeldinger = Lists.newArrayListWithExpectedSize(personer.size());

        for (Person person : personer) {
            Doedsmelding newDoedsmelding = new Doedsmelding();
            newDoedsmelding.setPerson(person);
            newDoedsmelding.setIsMeldingSent(MELDING_SENT_CONFIRMED);
            doedsmeldinger.add(newDoedsmelding);
        }
        doedsmeldingRepository.save(doedsmeldinger);
    }
}
