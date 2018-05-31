package no.nav.tps.forvalteren.domain.service.tps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStatus {

    private String kode;
    private String melding;
    private String utfyllendeMelding;

}
