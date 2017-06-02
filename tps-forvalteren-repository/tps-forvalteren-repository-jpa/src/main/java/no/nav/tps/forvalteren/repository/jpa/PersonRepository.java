package no.nav.tps.forvalteren.repository.jpa;

import no.nav.tps.forvalteren.domain.jpa.Person;
import org.springframework.data.repository.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface PersonRepository extends Repository<Person, Long> {

    List<Person> findAll();

    @Transactional
    void deleteByIdIn(Long[] ids);

}