package no.nav.tps.forvalteren.repository.jpa;

import no.nav.tps.forvalteren.domain.jpa.Person;
import org.springframework.data.repository.Repository;

import java.util.List;

@FunctionalInterface
public interface PersonRepository extends Repository<Person, Long> {

    List<Person> findAll();

}
