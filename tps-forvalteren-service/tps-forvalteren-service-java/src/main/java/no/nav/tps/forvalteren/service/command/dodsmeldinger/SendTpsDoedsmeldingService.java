package no.nav.tps.forvalteren.service.command.dodsmeldinger;

import static java.lang.String.format;
import static no.nav.tps.forvalteren.domain.service.tps.servicerutiner.definition.resolvers.skdmeldinger.DoedsmeldingAarsakskode43.DOEDSMELDING_MLD_NAVN;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.domain.rs.skd.DoedsmeldingHandlingType;
import no.nav.tps.forvalteren.domain.rs.skd.RsTpsDoedsmeldingRequest;
import no.nav.tps.forvalteren.service.command.exceptions.TpsfFunctionalException;
import no.nav.tps.forvalteren.service.command.exceptions.TpsfTechnicalException;
import no.nav.tps.forvalteren.service.command.testdata.response.lagretiltps.SendSkdMeldingTilTpsResponse;
import no.nav.tps.forvalteren.service.command.testdata.utils.ExtractErrorStatus;
import no.nav.tps.forvalteren.service.command.testdata.utils.IdenttypeFraIdentUtil;
import no.nav.tps.xjc.ctg.domain.s004.PersondataFraTpsS004;

@Service
public class SendTpsDoedsmeldingService extends SendDodsmeldingTilTpsService {

    public SendSkdMeldingTilTpsResponse sendDoedsmelding(RsTpsDoedsmeldingRequest request) {
        validate(request);

        Person person = new Person();
        person.setIdent(request.getIdent());
        person.setIdenttype(IdenttypeFraIdentUtil.getIdenttype(request.getIdent()));
        person.setRegdato(LocalDateTime.now());

        Map<String, String> sentStatus = new HashMap<>(request.getMiljoer().size());
        for (String miljoe : request.getMiljoer()) {
            PersondataFraTpsS004 persondataFraTps;
            try {
                persondataFraTps = hentPersonstatus(request.getIdent(), miljoe);
                sentStatus.putAll(sendAnnulering(person, persondataFraTps.getDatoDo(), request.getHandling().name(), miljoe));
                sentStatus.putAll(sendDoedsmelding(person, persondataFraTps.getDatoDo(), request.getDoedsdato(), request.getHandling().name(), miljoe));

            } catch (TpsfFunctionalException | TpsfTechnicalException e) {
                sentStatus.put(miljoe, format("FEIL: %s", e.getMessage()));
            }
        }
        return prepareStatus(sentStatus, person.getIdent(), request.getHandling());
    }

    private void validate(RsTpsDoedsmeldingRequest request) {
        if (!request.validatesOk()) {
            throw new TpsfFunctionalException("Påkrevet parameter mangler.");
        }
    }

    private SendSkdMeldingTilTpsResponse prepareStatus(Map<String, String> sentStatus, String ident, DoedsmeldingHandlingType type) {
        sentStatus.replaceAll((env, status) -> status.matches("^00.*") ? "OK" : ExtractErrorStatus.extract(status));
        return SendSkdMeldingTilTpsResponse.builder()
                .personId(ident)
                .skdmeldingstype(format("%s %s", DOEDSMELDING_MLD_NAVN, type.getHandling().substring(type.getHandling().indexOf("-- "))))
                .status(sentStatus)
                .build();
    }
}