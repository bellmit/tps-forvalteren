package no.nav.tps.forvalteren.service.command.testdata.opprett.implementation;

import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.repository.jpa.PersonRepository;
import no.nav.tps.forvalteren.service.command.testdata.opprett.FindIdenterNotUsedInDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultFindIdenterNotUsedInDB implements FindIdenterNotUsedInDB {

    @Autowired
    private PersonRepository repository;

    public Set<String> filtrer(Set<String> identer) {
        List<String> identListe = new ArrayList<>(identer);
        List<Person> personerSomFinnes = repository.findByIdentIn(identListe);
        List<String> opptatteIdenter = personerSomFinnes.stream()
                .map(Person::getIdent)
                .collect(Collectors.toList());

        identListe.removeAll(opptatteIdenter);
        return new HashSet<>(identListe);
    }

}