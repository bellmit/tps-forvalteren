package no.nav.tps.forvalteren.service.command.innvandring;

import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.repository.jpa.PersonRepository;
import no.nav.tps.forvalteren.service.command.testdata.utils.HentDatoFraIdentService;
import no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.utils.LandkodeEncoder;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddInndringsdatoOgLandTilPersonerService {

    @Autowired
    private LandkodeEncoder landkodeEncoder;

    @Autowired
    private HentDatoFraIdentService hentDatoFraIdent;

    @Autowired
    private PersonRepository personRepository;

    public void execute(List<Person> personer){
        personer.forEach(person -> {
            if(person.getInnvandretFraLand() == null){
                person.setInnvandretFraLand("106");
//                person.setInnvandretFraLand(landkodeEncoder.getRandomLandTla());
            }
            person.setInnvandretFraLandFlyttedato(hentDatoFraIdent.extract(person.getIdent()));
        });

        personRepository.save(personer);
    }
}
