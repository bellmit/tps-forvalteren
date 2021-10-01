package no.nav.tps.forvalteren.consumer.rs.identpool.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import no.nav.tps.forvalteren.domain.rs.skd.IdentType;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IdentpoolNewIdentsRequest {

    private Long antall;
    private LocalDate foedtEtter;
    private LocalDate foedtFoer;
    private IdentType identtype;
    private IdentpoolKjoenn kjoenn;
    private String rekvirertAv;
    private Boolean syntetisk;
}
