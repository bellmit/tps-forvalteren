package no.nav.tps.vedlikehold.domain.service.command.tps.servicerutiner.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TpsServiceRoutineEndringRequest extends TpsServiceRoutineRequest {

    private String kilde;
}