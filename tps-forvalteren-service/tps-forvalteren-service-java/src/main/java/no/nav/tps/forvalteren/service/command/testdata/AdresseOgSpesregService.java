package no.nav.tps.forvalteren.service.command.testdata;

import no.nav.tps.forvalteren.domain.jpa.Adresse;
import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.service.command.testdata.opprett.DummyAdresseService;
import no.nav.tps.forvalteren.service.command.testdata.utils.HentDatoFraIdentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static no.nav.tps.forvalteren.domain.rs.skd.IdentType.DNR;
import static no.nav.tps.forvalteren.domain.service.DiskresjonskoderType.UFB;
import static no.nav.tps.forvalteren.service.command.testdata.opprett.DummyAdresseService.SPSF_ADR;
import static no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.utils.NullcheckUtil.nullcheckSetDefaultValue;

@Service
public class AdresseOgSpesregService {

    @Autowired
    private DummyAdresseService dummyAdresseService;

    @Autowired
    private HentDatoFraIdentService hentDatoFraIdentService;

    public Person updateAdresseOgSpesregAttributes(Person person) {

        if (DNR.name().equals(person.getIdenttype())) {

            person.getPostadresse().clear();
            person.getPostadresse().add(dummyAdresseService.createDummyPostAdresseUtland(person));
            person.setSpesreg(null);
            person.setEgenAnsattDatoFom(null);
            person.setUtenFastBopel(null);

            person.setBoadresse(null);

        } else if (person.isKode6()) {

            person.getPostadresse().clear();
            person.getPostadresse().add(dummyAdresseService.createDummyPostAdresse(person));

            person.setBoadresse(null);
            person.setSpesregDato(nullcheckSetDefaultValue(person.getSpesregDato(), hentDatoFraIdentService.extract(person.getIdent())));

        } else if (person.isUtenFastBopel()) {

            Adresse adresse = dummyAdresseService.createAdresseUfb(person, person.getBoadresse().get(0));
            person.getBoadresse().iterator().remove();
            person.getBoadresse().add(adresse);
            person.setSpesreg(nullcheckSetDefaultValue(person.getSpesreg(), UFB.name()));
            person.setSpesregDato(nullcheckSetDefaultValue(person.getSpesregDato(), hentDatoFraIdentService.extract(person.getIdent())));

        } else if (person.getBoadresse().isEmpty() && !person.isUtenlandskadresse()) {

            person.getBoadresse().add(dummyAdresseService.createDummyBoAdresse(person));
            person.setSpesregDato(null);
            if (!person.getPostadresse().isEmpty() && SPSF_ADR.equals(person.getPostadresse().get(0).getPostLinje1())) {
                person.getPostadresse().clear();
            }
        }

        person.getPostadresse().forEach(adresse -> adresse.setPerson(person));
        if (!person.getBoadresse().isEmpty()) {
            person.getBoadresse().get(0).setPerson(person);
        }

        person.setGtVerdi(null); // Triggers reload of TKNR

        return person;
    }
}
