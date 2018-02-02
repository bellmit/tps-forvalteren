package no.nav.tps.forvalteren.provider.rs.api.v1.endpoints;

import static no.nav.tps.forvalteren.provider.rs.config.ProviderConstants.OPERATION;
import static no.nav.tps.forvalteren.provider.rs.config.ProviderConstants.RESTSERVICE;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import ma.glasnost.orika.MapperFacade;
import no.nav.freg.metrics.annotations.Metrics;
import no.nav.freg.spring.boot.starters.log.exceptions.LogExceptions;
import no.nav.tps.forvalteren.domain.jpa.DeathRow;
import no.nav.tps.forvalteren.domain.rs.RsDeathRow;
import no.nav.tps.forvalteren.domain.rs.RsDeathRowBulk;
import no.nav.tps.forvalteren.service.command.dodsmeldinger.CreateDodsmelding;
import no.nav.tps.forvalteren.service.command.dodsmeldinger.FindAllDeathRows;
import no.nav.tps.forvalteren.service.command.dodsmeldinger.UpdateDeathRow;
import no.nav.tps.forvalteren.service.command.testdata.SjekkIdenterForDodsmelding;
import no.nav.tps.forvalteren.service.command.testdata.response.IdentMedStatus;

@Transactional
@RestController
@RequestMapping(value = "api/v1/doedsmelding")
@ConditionalOnProperty(prefix = "tps.forvalteren", name = "production-mode", havingValue = "false")
public class DeathRowController {

    private static final String REST_SERVICE_NAME = "testdata";

    @Autowired
    private MapperFacade mapper;

    @Autowired
    private CreateDodsmelding createDodsmelding;

    @Autowired
    private SjekkIdenterForDodsmelding sjekkIdenterForDodsmelding;

    @Autowired
    private FindAllDeathRows findAllDeathRows;

    @Autowired
    private UpdateDeathRow updateDeathRow;

    @LogExceptions
    @Metrics(value = "provider", tags = { @Metrics.Tag(key = RESTSERVICE, value = REST_SERVICE_NAME), @Metrics.Tag(key = OPERATION, value = "sjekkIdenter")})
    @RequestMapping(value = "/checkpersoner", method = RequestMethod.POST)
    public Set<IdentMedStatus> checkIdList(@RequestBody RsDeathRowBulk rsDeathRowBulk) {
        return sjekkIdenterForDodsmelding.finnGyldigeOgLedigeIdenterForDoedsmeldinger(rsDeathRowBulk.getIdenter(), rsDeathRowBulk.getMiljoe());
    }


    @LogExceptions
    @Metrics(value = "provider", tags = { @Metrics.Tag(key = RESTSERVICE, value = REST_SERVICE_NAME), @Metrics.Tag(key = OPERATION, value = "settDodsmelding")})
    @RequestMapping(value = "/opprett", method = RequestMethod.POST)
    public void createMelding(@RequestBody RsDeathRowBulk rsDeathRowBulk) { //RsDeathRow dMelding){
        List<DeathRow> deathRowList = mapper.map(rsDeathRowBulk, List.class);
        for(DeathRow deathrow : deathRowList){
            createDodsmelding.execute(deathrow);
        }
    }

    @LogExceptions
    @Metrics(value = "provider", tags = { @Metrics.Tag(key = RESTSERVICE, value = REST_SERVICE_NAME), @Metrics.Tag(key = OPERATION, value = "annullerDodsmelding")})
    @RequestMapping(value = "/deletemelding", method = RequestMethod.POST)
    public void deleteMelding(){

    }

    @LogExceptions
    @Metrics(value = "provider", tags = { @Metrics.Tag(key = RESTSERVICE, value = REST_SERVICE_NAME), @Metrics.Tag(key = OPERATION, value = "hentLogg")})
    @RequestMapping(value = "/meldinger", method = RequestMethod.GET)
    public List<RsDeathRow> getMeldingLogg() {
        List<DeathRow> deathRowList = findAllDeathRows.execute();
        return mapper.mapAsList(deathRowList, RsDeathRow.class);
    }

    @Transactional
    @LogExceptions
    @PutMapping(value = "/oppdater")
    @Metrics(value = "provider", tags = { @Metrics.Tag(key = RESTSERVICE, value = REST_SERVICE_NAME), @Metrics.Tag(key = OPERATION, value = "oppdaterMelding") })
    public RsDeathRow updateMelding(@RequestBody RsDeathRow rsDeathRow) {
        DeathRow mappedDeathRow = mapper.map(rsDeathRow, DeathRow.class);
        DeathRow updatedDeathRow = updateDeathRow.execute(mappedDeathRow);
        return mapper.map(updatedDeathRow, RsDeathRow.class);
    }
}
