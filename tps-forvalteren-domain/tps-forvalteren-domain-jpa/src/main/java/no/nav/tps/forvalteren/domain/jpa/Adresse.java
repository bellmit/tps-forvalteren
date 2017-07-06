package no.nav.tps.forvalteren.domain.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Adresse {

    private static final String SEQ = "ADRESSE_SEQ";

    @Id
    @SequenceGenerator(name = SEQ, sequenceName = SEQ, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @Column(name = "KOMMUNENR", length = 4)
    private String kommunenr;

    @Column(name = "FLYTTE_DATO")
    private LocalDateTime flyttedato;

    @Column(name = "POSTNR", length = 4)
    private String postnr;

}
