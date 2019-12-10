package no.nav.tps.forvalteren.domain.jpa;

import static java.util.Objects.isNull;
import static javax.persistence.CascadeType.ALL;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.nav.tps.forvalteren.domain.jpa.embedded.ChangeStamp;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_PERSON")
public class Person extends ChangeStamp {

    private static final String SEQ = "T_PERSON_SEQ";

    @Id
    @SequenceGenerator(name = SEQ, sequenceName = SEQ, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ)
    @Column(name = "PERSON_ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "IDENT", nullable = false, unique = true, length = 11)
    private String ident;

    @Column(name = "IDENTTYPE", nullable = false, length = 3)
    private String identtype;

    @Column(name = "KJONN", nullable = false)
    private String kjonn;

    @Column(name = "FORNAVN", nullable = false, length = 50)
    private String fornavn;

    @Column(name = "MELLOMNAVN", length = 50)
    private String mellomnavn;

    @Column(name = "ETTERNAVN", nullable = false, length = 50)
    private String etternavn;

    @Column(name = "FORKORTET_NAVN")
    private String forkortetNavn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = ALL)
    private List<Statsborgerskap> statsborgerskap;

    @Column(name = "SPESREG", length = 1)
    private String spesreg;

    @Column(name = "SPESREG_DATO")
    private LocalDateTime spesregDato;

    @Column(name = "DOEDSDATO")
    private LocalDateTime doedsdato;

    @Column(name = "SIVILSTAND", length = 4)
    private String sivilstand;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = ALL)
    private List<Sivilstand> sivilstander;

    @Column(name = "SIVILSTAND_REGDATO")
    private LocalDateTime sivilstandRegdato;

    @Column(name = "INNVANDRET_FRA_LAND", length = 3)
    private String innvandretFraLand;

    @Column(name = "INNVANDRET_FRA_LAND_FLYTTEDATO")
    private LocalDateTime innvandretFraLandFlyttedato;

    @Column(name = "INNVANDRET_FRA_LAND_REGDATO")
    private LocalDateTime innvandretFraLandRegdato;

    @Column(name = "UTVANDRET_TIL_LAND", length = 3)
    private String utvandretTilLand;

    @Column(name = "UTVANDRET_TIL_LAND_FLYTTEDATO")
    private LocalDateTime utvandretTilLandFlyttedato;

    @Column(name = "UTVANDRET_TIL_LAND_REGDATO")
    private LocalDateTime utvandretTilLandRegdato;

    @Column(name = "EGEN_ANSATT_DATO_FOM")
    private LocalDateTime egenAnsattDatoFom;

    @Column(name = "EGEN_ANSATT_DATO_TOM")
    private LocalDateTime egenAnsattDatoTom;

    @Column(name = "TYPE_SIKKERHETSTILTAK", length = 4)
    private String typeSikkerhetsTiltak;

    @Column(name = "SIKKERHETSTILTAK_DATO_FOM")
    private LocalDateTime sikkerhetsTiltakDatoFom;

    @Column(name = "SIKKERHETSTILTAK_DATO_TOM")
    private LocalDateTime sikkerhetsTiltakDatoTom;

    @Column(name = "BESKR_SIKKERHETSTILTAK", length = 50)
    private String beskrSikkerhetsTiltak;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = ALL)
    private List<Adresse> boadresse;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = ALL)
    private List<Postadresse> postadresse;

    @Column(name = "REGDATO", nullable = false)
    private LocalDateTime regdato;

    @JoinColumn(name = "GRUPPE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Gruppe gruppe;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = ALL)
    private List<Relasjon> relasjoner;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person", cascade = ALL)
    private List<IdentHistorikk> identHistorikk;

    @Column(name = "OPPRETTET_DATO")
    private LocalDateTime opprettetDato;

    @Column(name = "OPPRETTET_AV")
    private String opprettetAv;

    @Column(name = "SPRAK_KODE")
    private String sprakKode;

    @Column(name = "DATO_SPRAK")
    private LocalDateTime datoSprak;

    @Column(name = "TKNR")
    private String tknr;

    @Column(name = "TKNAVN")
    private String tknavn;

    @Column(name = "GT_TYPE")
    private String gtType;

    @Column(name = "GT_VERDI")
    private String gtVerdi;

    @Column(name = "GT_REGEL")
    private String gtRegel;

    @Column(name = "UTEN_FAST_BOPEL")
    private Boolean utenFastBopel;

    @Column(name = "PERSON_STATUS")
    private String personStatus;

    @Column(name = "FORSVUNNET_DATO")
    private LocalDateTime forsvunnetDato;

    @Transient
    private String replacedByIdent;

    @Transient
    private LocalDateTime aliasRegdato;

    public List<Postadresse> getPostadresse() {
        if (isNull(postadresse)) {
            postadresse = new ArrayList();
        }
        return postadresse;
    }

    public List<Relasjon> getRelasjoner() {
        if (isNull(relasjoner)) {
            relasjoner = new ArrayList();
        }
        return relasjoner;
    }

    public List<IdentHistorikk> getIdentHistorikk() {
        if (isNull(identHistorikk)) {
            identHistorikk = new ArrayList();
        }
        return identHistorikk;
    }

    public List<Sivilstand> getSivilstander() {
        if (isNull(sivilstander)) {
            sivilstander = new ArrayList();
        }
        return sivilstander;
    }

    public List<Adresse> getBoadresse() {
        if (isNull(boadresse)) {
            boadresse = new ArrayList();
        }
        return boadresse;
    }

    public List<Statsborgerskap> getStatsborgerskap() {
        if (isNull(statsborgerskap)) {
            statsborgerskap = new ArrayList();
        }
        return statsborgerskap;
    }

    public Person toUppercase() {
        if (isNotBlank(getFornavn())) {
            setFornavn(getFornavn().toUpperCase());
        }
        if (isNotBlank(getMellomnavn())) {
            setMellomnavn(getMellomnavn().toUpperCase());
        }
        if (isNotBlank(getEtternavn())) {
            setEtternavn(getEtternavn().toUpperCase());
        }
        if (isNotBlank(getForkortetNavn())) {
            setForkortetNavn(getForkortetNavn().toUpperCase());
        }
        if (isNotBlank(getIdenttype())) {
            setIdenttype(getIdenttype().toUpperCase());
        }

        getBoadresse().forEach(Adresse::toUppercase);
        getPostadresse().forEach(Postadresse::toUppercase);

        return this;
    }

    public Person sorterPersondetaljer() {
        getIdentHistorikk().sort(Comparator.comparing(IdentHistorikk::getHistoricIdentOrder).reversed());
        getSivilstander().sort(Comparator.comparing(Sivilstand::getSivilstandRegdato).reversed());
        getBoadresse().sort(Comparator.comparing(Adresse::getId).reversed());
        getPostadresse().sort(Comparator.comparing(Postadresse::getId).reversed());
        return this;
    }
}