package no.nav.tps.forvalteren.service.command.endringsmeldinger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.nav.tps.forvalteren.domain.jpa.SkdEndringsmelding;
import no.nav.tps.forvalteren.domain.jpa.SkdEndringsmeldingGruppe;
import no.nav.tps.forvalteren.domain.rs.skd.RsMeldingstype;
import no.nav.tps.forvalteren.repository.jpa.SkdEndringsmeldingGruppeRepository;
import no.nav.tps.forvalteren.repository.jpa.SkdEndringsmeldingRepository;

@Service
public class SkdEndringsmeldingService {
    
    @Autowired
    private SkdEndringsmeldingRepository skdEndringsmeldingRepository;
    
    @Autowired
    private SkdEndringsmeldingGruppeRepository gruppeRepository;
    
    @Autowired
    private ConvertJsonToRsMeldingstype convertJsonToRsMeldingstype;
    
    public List<RsMeldingstype> findAllSkdmeldingerByGruppeIdOrderByIdAsc(Long gruppeId) {
        SkdEndringsmeldingGruppe gruppe = gruppeRepository.findById(gruppeId);
        List<SkdEndringsmelding> skdEndringsmeldingList = skdEndringsmeldingRepository.findAllByGruppeOrderByIdAsc(gruppe);
        List<RsMeldingstype> rsSkdmeldingene = new ArrayList<>();
        for (SkdEndringsmelding mld : skdEndringsmeldingList) {
            rsSkdmeldingene.add(convertJsonToRsMeldingstype.execute(mld));
        }
        return rsSkdmeldingene;
    }
    
    public Set<String> filtrerIdenterPaaAarsakskodeOgTransaksjonstype(Long gruppeId, List<String> aarsakskoder, String transaksjonstype) {
        SkdEndringsmeldingGruppe gruppe = gruppeRepository.findById(gruppeId);
        List<SkdEndringsmelding> meldinger = skdEndringsmeldingRepository.findByAarsakskodeInAndTransaksjonstypeAndGruppe(aarsakskoder, transaksjonstype, gruppe);
        return meldinger.stream().map(SkdEndringsmelding::getFoedselsnummer).collect(Collectors.toSet());
    }
}
