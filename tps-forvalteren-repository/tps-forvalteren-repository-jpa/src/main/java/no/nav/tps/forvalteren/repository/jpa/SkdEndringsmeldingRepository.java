package no.nav.tps.forvalteren.repository.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import no.nav.tps.forvalteren.domain.jpa.SkdEndringsmelding;
import no.nav.tps.forvalteren.domain.jpa.SkdEndringsmeldingGruppe;

public interface SkdEndringsmeldingRepository extends Repository<SkdEndringsmelding, Long> {

    SkdEndringsmelding findById(Long id);

    SkdEndringsmelding save(SkdEndringsmelding skdEndringsmelding);

    void deleteByIdIn(List<Long> ids);

    List<SkdEndringsmelding> findAllByGruppe(SkdEndringsmeldingGruppe gruppe);

    void deleteAll();

    @Query("SELECT foedselsnummer FROM SkdEndringsmelding "
            + "WHERE aarsakskode in :aarsakskoder AND transaksjonstype=:transaksjonstype AND gruppe=:gruppe")
    List<String> findFoedselsnummerBy(
            @Param("aarsakskoder") List<String> aarsakskoder,
            @Param("transaksjonstype") String transaksjonstype,
            @Param("gruppe") SkdEndringsmeldingGruppe gruppe);
}
