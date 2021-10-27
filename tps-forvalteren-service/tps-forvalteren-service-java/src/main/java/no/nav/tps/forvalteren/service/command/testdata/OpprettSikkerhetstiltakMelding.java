package no.nav.tps.forvalteren.service.command.testdata;

import static java.util.Objects.nonNull;
import static no.nav.tps.forvalteren.domain.service.tps.servicerutiner.definition.resolvers.navmeldinger.EndreSikkerhetstiltak.SIKKERHETSTILTAK_MLD_NAVN;
import static no.nav.tps.forvalteren.domain.service.tps.servicerutiner.definition.resolvers.navmeldinger.OpphoerSikkerhetstiltak.SIKKERHETSTILTAK_OPPHOERSMELDING;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.requests.endring.TpsEndreSikkerhetstiltakRequest;
import no.nav.tps.forvalteren.domain.service.tps.servicerutiner.requests.endring.TpsOpphorSikkerhetsTiltakRequest;
import no.nav.tps.forvalteren.service.command.testdata.skd.TpsNavEndringsMelding;
import no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.utils.ConvertDateToString;

@Service
public class OpprettSikkerhetstiltakMelding {

    private static final String CANCEL_SIKKERHETSTILTAK = "OPPHØRT";

    public List<TpsNavEndringsMelding> execute(Person person, Set<String> environmentSet) {
        List<TpsNavEndringsMelding> navMeldinger = new ArrayList<>();
        
        if (sjekkForSikkerhetstiltak(person)) {
            environmentSet.forEach(environment -> {

                if (CANCEL_SIKKERHETSTILTAK.equalsIgnoreCase(person.getBeskrSikkerhetTiltak())) {
                    navMeldinger.add(new TpsNavEndringsMelding(TpsOpphorSikkerhetsTiltakRequest.builder()
                            .serviceRutinenavn(SIKKERHETSTILTAK_OPPHOERSMELDING)
                            .offentligIdent(person.getIdent())
                            .build(), environment));

                    person.setBeskrSikkerhetTiltak(null);
                    person.setBeskrSikkerhetTiltak(null);
                    person.setSikkerhetTiltakDatoFom(null);
                    person.setSikkerhetTiltakDatoTom(null);

                } else {
                    navMeldinger.add(new TpsNavEndringsMelding(TpsEndreSikkerhetstiltakRequest.builder()
                            .serviceRutinenavn(SIKKERHETSTILTAK_MLD_NAVN)
                            .offentligIdent(person.getIdent())
                            .typeSikkerhetsTiltak(person.getTypeSikkerhetTiltak())
                            .fom(ConvertDateToString.yyyysMMsdd(person.getSikkerhetTiltakDatoFom()))
                            .tom(ConvertDateToString.yyyysMMsdd(person.getSikkerhetTiltakDatoTom()))
                            .beskrSikkerhetsTiltak(person.getBeskrSikkerhetTiltak())
                            .build(), environment));
                }
            });
        }
        
        return navMeldinger;
    }

    private boolean sjekkForSikkerhetstiltak(Person person) {
        return isNotBlank(person.getTypeSikkerhetTiltak()) &&
                isNotBlank(person.getBeskrSikkerhetTiltak()) &&
                nonNull(person.getSikkerhetTiltakDatoFom());
    }
}
