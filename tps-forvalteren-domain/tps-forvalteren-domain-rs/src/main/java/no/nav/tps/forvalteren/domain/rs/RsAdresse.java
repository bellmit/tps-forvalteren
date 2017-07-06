package no.nav.tps.forvalteren.domain.rs;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RsGateadresse.class, name = "Gateadresse"),
        @JsonSubTypes.Type(value = RsMatrikkeladresse.class, name = "Matrikkeladresse")
})
@Getter
@Setter
public abstract class RsAdresse {

    @NotNull
    private Long id;

    @NotNull
    private RsSimplePerson person;

    @Size(min = 4, max = 4)
    private String postnr;

    @Size(min = 4, max = 4)
    private String kommunenr;

    private LocalDate flyttedato;

}
