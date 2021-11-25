package no.nav.tps.forvalteren.service.command.testdata.restreq;

import static com.google.common.collect.Lists.partition;
import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static no.nav.tps.forvalteren.domain.jpa.InnvandretUtvandret.InnUtvandret.UTVANDRET;
import static no.nav.tps.forvalteren.service.command.testdata.utils.TestdataConstants.ORACLE_MAX_IN_SET_ELEMENTS;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.nav.tps.forvalteren.domain.jpa.Fullmakt;
import no.nav.tps.forvalteren.domain.jpa.IdentHistorikk;
import no.nav.tps.forvalteren.domain.jpa.InnvandretUtvandret;
import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.domain.jpa.Relasjon;
import no.nav.tps.forvalteren.domain.jpa.Vergemaal;
import no.nav.tps.forvalteren.repository.jpa.IdenthistorikkRepository;
import no.nav.tps.forvalteren.repository.jpa.PersonRepository;
import no.nav.tps.forvalteren.service.IdentpoolService;
import no.nav.tps.forvalteren.service.command.exceptions.NotFoundException;
import no.nav.tps.forvalteren.service.command.tps.skdmelding.TpsPersonService;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final IdenthistorikkRepository identhistorikkRepository;
    private final IdentpoolService identpoolService;
    private final TpsPersonService tpsPersonService;
    private final DeletePersonService deletePersonService;

    @Transactional
    public List<Person> getPersonerByIdenter(List<String> identer) {

        //Begrenser maks antall identer i SQL spørring
        var personer = partition(identer, ORACLE_MAX_IN_SET_ELEMENTS).stream()
                .map(personRepository::findByIdentIn)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        personer.forEach(person -> {
            person.setPersonStatus(getPersonStatus(person));
        });

        return personer;
    }

    private String getPersonStatus(Person person) {

        if (nonNull(person.getDoedsdato())) {
            return "FDAT".equals(person.getIdenttype()) || "FNR".equals(person.getIdenttype()) ? "DØD" : "DØDD";

        } else if ("DNR".equals(person.getIdent())) {
            return "ADNR";

        } else if ("BOST".equals(person.getIdent())) {
            return "ABNR";

        } else if (nonNull(person.getForsvunnetDato())) {
            return "FOSV";

        } else if (UTVANDRET.equals(person.getInnvandretUtvandret().stream()
                .findFirst().orElse(new InnvandretUtvandret())
                .getInnutvandret())) {
            return "UTVA";

        } else if (!person.getBoadresse().isEmpty()) {
            return "BOSA";

        } else if ("FNR".equals(person.getIdenttype())) {
            return "FØDR";

        } else {
            return "UREG";
        }
    }

    public void deletePerson(List<String> miljoer, String ident) {

        deletePersonsContent(miljoer, getPersonsFromDb(singletonList(ident), true));
    }

    public void deletePersons(List<String> miljoer, List<String> identer) {

        deletePersonsContent(miljoer, getPersonsFromDb(identer, false));
    }

    private void deletePersonsContent(List<String> miljoer, Set<Person> persons) {

        log.info("Sletter person(er) med id {}", persons.stream().map(Person::getId)
                .map(id -> id.toString()).collect(Collectors.joining(", ")));
        try {
            persons.forEach(person -> deletePersonService.execute(person.getId()));
        } catch (DataIntegrityViolationException e) {
            log.info("Post delvis slettet, forsøker en gang til ...");
            persons.forEach(person -> deletePersonService.execute(person.getId()));
        }

        //Wipe persons in TPS
        tpsPersonService.sendDeletePersonMeldinger(miljoer, persons.stream()
                .map(Person::getIdent)
                .collect(Collectors.toSet()));

        identpoolService.recycleIdents(persons.stream()
                .map(Person::getIdent)
                .collect(Collectors.toSet()));
    }

    @Transactional
    public Set<Person> getPersonsFromDb(List<String> identer, boolean includeRelatedPeople) {

        List<Person> persons = personRepository.findByIdentIn(new HashSet<>(identer));

        if (persons.isEmpty()) {
            throw new NotFoundException("Ingen personer funnet");
        }

        return includeRelatedPeople ?

                Stream.of(
                                persons,
                                persons.stream()
                                        .map(Person::getRelasjoner)
                                        .flatMap(Collection::stream)
                                        .map(Relasjon::getPersonRelasjonMed)
                                        .collect(Collectors.toSet()),
                                persons.stream()
                                        .map(Person::getIdentHistorikk)
                                        .flatMap(Collection::stream)
                                        .map(IdentHistorikk::getAliasPerson)
                                        .collect(Collectors.toSet()),
                                persons.stream()
                                        .map(Person::getFullmakt)
                                        .flatMap(Collection::stream)
                                        .map(Fullmakt::getFullmektig)
                                        .collect(Collectors.toSet()),
                                persons.stream()
                                        .map(Person::getVergemaal)
                                        .flatMap(Collection::stream)
                                        .map(Vergemaal::getVerge)
                                        .collect(Collectors.toSet()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet()) :

                new HashSet<>(persons);
    }

    @Transactional
    public void deleteIdenthistorikk(List<Person> persons) {

        Set<Long> identhistorikkIds = new HashSet();
        Set<Long> personIds = new HashSet();
        Set<String> idents = new HashSet();

        persons.forEach(person -> {
            person.getIdentHistorikk().forEach(identHistorikk -> {
                identhistorikkIds.add(identHistorikk.getId());
                identHistorikk.getAliasPerson().getIdentHistorikk().forEach(identHistorikk2 ->
                        identhistorikkIds.add(identHistorikk2.getId()));
                personIds.add(identHistorikk.getAliasPerson().getId());
                idents.add(identHistorikk.getAliasPerson().getIdent());
                identHistorikk.getAliasPerson().getIdentHistorikk().clear();
            });
            person.getIdentHistorikk().clear();
        });

        if (!identhistorikkIds.isEmpty()) {
            identhistorikkRepository.deleteByIdIn(identhistorikkIds);
            personRepository.deleteByIdIn(personIds);
            identpoolService.recycleIdents(idents);
        }
    }

    public List<Person> searchPerson(String request) {
        Optional<String> ident = Stream.of(request.split(" "))
                .filter(StringUtils::isNumeric)
                .findFirst();

        List<String> navn = List.of(request.split(" ")).stream()
                .filter(fragment -> StringUtils.isNotBlank(fragment) && !StringUtils.isNumeric(fragment))
                .collect(Collectors.toList());

        return personRepository.findByWildcardIdent(ident.orElse(null),
                !navn.isEmpty() ? navn.get(0).toUpperCase() : null,
                navn.size() > 1 ? navn.get(1).toUpperCase() : null,
                PageRequest.of(0, 10));
    }
}
