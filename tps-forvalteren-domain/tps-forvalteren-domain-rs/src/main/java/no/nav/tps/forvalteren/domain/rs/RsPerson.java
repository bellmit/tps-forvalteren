package no.nav.tps.forvalteren.domain.rs;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RsPerson {

    private Long personId;

    @NotBlank
    @Size(min = 11, max = 11)
    private String ident;

    @NotBlank
    @Size(min = 3, max = 3)
    private String identtype;

    @NotBlank
    @Size(min = 3, max = 3)
    private String kjonn;

    @NotBlank
    @Size(min = 1, max = 50)
    private String fornavn;

    @Size(min = 1, max = 50)
    private String mellomnavn;

    @NotBlank
    @Size(min = 1, max = 15)
    private String etternavn;

    private String forkortetNavn;

    private String statsborgerskap;

    private LocalDateTime statsborgerskapRegdato;

    @Size(min = 1, max = 1)
    private String spesreg;

    private LocalDateTime spesregDato;

    private LocalDateTime doedsdato;

    private String sivilstand;

    private LocalDateTime sivilstandRegdato;

    @Size(max = 3)
    private String innvandretFraLand;

    private LocalDateTime innvandretFraLandFlyttedato;

    private LocalDateTime innvandretFraLandRegdato;

    @Size(max = 3)
    private String utvandretTilLand;

    private LocalDateTime utvandretTilLandFlyttedato;

    private LocalDateTime utvandretTilLandRegdato;

    private List<RsAdresse> boadresse;

    private List<RsPostadresse> postadresse;

    @NotNull
    private LocalDateTime regdato;

    private RsSimpleGruppe gruppe;

    private List<RsRelasjon> relasjoner;

    private LocalDateTime egenAnsattDatoFom;

    private LocalDateTime egenAnsattDatoTom;

    @Size(min = 4, max = 4)
    private String typeSikkerhetsTiltak;

    private LocalDateTime sikkerhetsTiltakDatoFom;

    private LocalDateTime sikkerhetsTiltakDatoTom;

    @Size(min = 1, max = 50)
    private String beskrSikkerhetsTiltak;

    private LocalDateTime foedselsdato;

    private Integer alder;

    private LocalDateTime opprettetDato;

    private String opprettetAv;

    private String sprakKode;

    private LocalDateTime datoSprak;

    private String tknr;

    private String tknavn;

    private String gtType;

    private String gtVerdi;

    private String gtRegel;

    private Boolean utenFastBopel;

    private String personStatus;

    private String forsvunnetDato;

    private List<RsIdenthistorikk> identHistorikk;

    private List<RsSivilstand> sivilstander;

    public List<RsRelasjon> getRelasjoner() {
        if (isNull(relasjoner)) {
            relasjoner = new ArrayList();
        }
        return relasjoner;
    }

    public List<RsIdenthistorikk> getIdentHistorikk() {
        if (isNull(identHistorikk)) {
            identHistorikk = new ArrayList();
        }
        return identHistorikk;
    }

    public RsPerson sorterPersondetaljer() {
        getIdentHistorikk().sort(Comparator.comparing(RsIdenthistorikk::getHistoricIdentOrder).reversed());
        getSivilstander().sort(Comparator.comparing(RsSivilstand::getSivilstandRegdato).reversed());
        getBoadresse().sort(Comparator.comparing(RsAdresse::getAdresseId).reversed());
        getPostadresse().sort(Comparator.comparing(RsPostadresse::getId).reversed());
        return this;
    }
}
