package no.nav.tps.forvalteren.repository.jpa;

import org.springframework.data.repository.Repository;

import no.nav.tps.forvalteren.domain.jpa.Doedsmelding;

public interface DoedsmeldingRepository extends Repository<Doedsmelding, Long> {

    Doedsmelding findDoedsmeldingByPersonId(Long id);

    void save(Iterable<Doedsmelding> doedsmeldinger);

    void deleteById(Long id);
}
