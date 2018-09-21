package no.nav.tps.forvalteren.service.command.testdata.skd;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkdOpprettSkdMeldingMedHeaderOgInnhold {

    @Autowired
    private SkdInputParamsToSkdMeldingInnhold skdInputParamsToSkdMeldingInnhold;

    @Autowired
    private SkdAddHeaderToSkdMelding skdAddHeaderToSkdMelding;

    public String execute(Map<String, String> skdParameters, SkdFelterContainer skdFelterContainer, boolean addHeader) {
        StringBuilder skdMelding = skdInputParamsToSkdMeldingInnhold.execute(skdParameters, skdFelterContainer);
        if (addHeader) {
            StringBuilder skdMeldingMedHeader = skdAddHeaderToSkdMelding.execute(skdMelding);
            return skdMeldingMedHeader.toString();
        }
        return skdMelding.toString();
    }
}
