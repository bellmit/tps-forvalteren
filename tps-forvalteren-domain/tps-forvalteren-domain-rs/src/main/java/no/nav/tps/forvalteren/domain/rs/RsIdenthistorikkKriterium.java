package no.nav.tps.forvalteren.domain.rs;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RsIdenthistorikkKriterium {

    private String identtype;
    private LocalDateTime foedtEtter;
    private LocalDateTime foedtFoer;
    private String kjonn;
    private LocalDateTime regdato;
}