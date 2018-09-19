package no.nav.tps.forvalteren.service.command.testdata.utils;

import static org.junit.Assert.assertEquals;

import no.nav.tps.forvalteren.service.command.testdata.skd.SkdMeldingTrans1;

public class AssertEqualSkdMeldingTrans1 {
	
	public static void assertSdkmelding(SkdMeldingTrans1 expectedSkdMeldingTrans1, SkdMeldingTrans1 actualSkdMeldingTrans1) {
		
		assertEquals("header",expectedSkdMeldingTrans1.getHeader(), actualSkdMeldingTrans1.getHeader());
		assertEquals("fodselsdato",expectedSkdMeldingTrans1.getFodselsdato(), actualSkdMeldingTrans1.getFodselsdato());
		assertEquals("personnummer",expectedSkdMeldingTrans1.getPersonnummer(), actualSkdMeldingTrans1.getPersonnummer());
		assertEquals("maskindato",expectedSkdMeldingTrans1.getMaskindato(), actualSkdMeldingTrans1.getMaskindato());
		assertEquals("maskintid",expectedSkdMeldingTrans1.getMaskintid(), actualSkdMeldingTrans1.getMaskintid());
		assertEquals("transtype",expectedSkdMeldingTrans1.getTranstype(), actualSkdMeldingTrans1.getTranstype());
		assertEquals("aarsakskode",expectedSkdMeldingTrans1.getAarsakskode(), actualSkdMeldingTrans1.getAarsakskode());
		assertEquals("regDato",expectedSkdMeldingTrans1.getRegDato(), actualSkdMeldingTrans1.getRegDato());
		assertEquals("statuskode",expectedSkdMeldingTrans1.getStatuskode(), actualSkdMeldingTrans1.getStatuskode());
		assertEquals("datoDoed",expectedSkdMeldingTrans1.getDatoDoed(), actualSkdMeldingTrans1.getDatoDoed());
		assertEquals("slektsnavn",expectedSkdMeldingTrans1.getSlektsnavn(), actualSkdMeldingTrans1.getSlektsnavn());
		assertEquals("fornavn",expectedSkdMeldingTrans1.getFornavn(), actualSkdMeldingTrans1.getFornavn());
		assertEquals("mellomnavn",expectedSkdMeldingTrans1.getMellomnavn(), actualSkdMeldingTrans1.getMellomnavn());
		assertEquals("slekstnavnUgiftT",expectedSkdMeldingTrans1.getSlekstnavnUgift(), actualSkdMeldingTrans1.getSlekstnavnUgift());
		assertEquals("forkortetNavn",expectedSkdMeldingTrans1.getForkortetNavn(), actualSkdMeldingTrans1.getForkortetNavn());
		assertEquals("regDatoNavn",expectedSkdMeldingTrans1.getRegDatoNavn(), actualSkdMeldingTrans1.getRegDatoNavn());
		assertEquals("foedekommLand",expectedSkdMeldingTrans1.getFoedekommLand(), actualSkdMeldingTrans1.getFoedekommLand());
		assertEquals("foedested",expectedSkdMeldingTrans1.getFoedested(), actualSkdMeldingTrans1.getFoedested());
		assertEquals("statsborgerskap",expectedSkdMeldingTrans1.getStatsborgerskap(), actualSkdMeldingTrans1.getStatsborgerskap());
		assertEquals("statsborgerskapRegdato",expectedSkdMeldingTrans1.getStatsborgerskapRegdato(), actualSkdMeldingTrans1.getStatsborgerskapRegdato());
		assertEquals("familienummer",expectedSkdMeldingTrans1.getFamilienummer(), actualSkdMeldingTrans1.getFamilienummer());
		assertEquals("regdatoFamnr",expectedSkdMeldingTrans1.getRegdatoFamnr(), actualSkdMeldingTrans1.getRegdatoFamnr());
		assertEquals("personkode",expectedSkdMeldingTrans1.getPersonkode(), actualSkdMeldingTrans1.getPersonkode());
		assertEquals("spesRegType",expectedSkdMeldingTrans1.getSpesRegType(), actualSkdMeldingTrans1.getSpesRegType());
		assertEquals("datoSpesRegType",expectedSkdMeldingTrans1.getDatoSpesRegType(), actualSkdMeldingTrans1.getDatoSpesRegType());
		assertEquals("sivilstand",expectedSkdMeldingTrans1.getSivilstand(), actualSkdMeldingTrans1.getSivilstand());
		assertEquals("regdatoSivilstand",expectedSkdMeldingTrans1.getRegdatoSivilstand(), actualSkdMeldingTrans1.getRegdatoSivilstand());
		assertEquals("ektefellePartnerFoedselsdato",expectedSkdMeldingTrans1.getEktefellePartnerFoedselsdato(), actualSkdMeldingTrans1.getEktefellePartnerFoedselsdato());
		assertEquals("ektefellePartnerPersonnr",expectedSkdMeldingTrans1.getEktefellePartnerPersonnr(), actualSkdMeldingTrans1.getEktefellePartnerPersonnr());
		assertEquals("ektefellePartnerNavn",expectedSkdMeldingTrans1.getEktefellePartnerNavn(), actualSkdMeldingTrans1.getEktefellePartnerNavn());
		assertEquals("ektefellePartnerStatsb",expectedSkdMeldingTrans1.getEktefellePartnerStatsb(), actualSkdMeldingTrans1.getEktefellePartnerStatsb());
		assertEquals("regdatoAdr",expectedSkdMeldingTrans1.getRegdatoAdr(), actualSkdMeldingTrans1.getRegdatoAdr());
		assertEquals("flyttedatoAdr",expectedSkdMeldingTrans1.getFlyttedatoAdr(), actualSkdMeldingTrans1.getFlyttedatoAdr());
		assertEquals("kommunenummer",expectedSkdMeldingTrans1.getKommunenummer(), actualSkdMeldingTrans1.getKommunenummer());
		assertEquals("gateGaard",expectedSkdMeldingTrans1.getGateGaard(), actualSkdMeldingTrans1.getGateGaard());
		assertEquals("husBruk",expectedSkdMeldingTrans1.getHusBruk(), actualSkdMeldingTrans1.getHusBruk());
		assertEquals("bokstavFestenr",expectedSkdMeldingTrans1.getBokstavFestenr(), actualSkdMeldingTrans1.getBokstavFestenr());
		assertEquals("undernr",expectedSkdMeldingTrans1.getUndernr(), actualSkdMeldingTrans1.getUndernr());
		assertEquals("adressenavn",expectedSkdMeldingTrans1.getAdressenavn(), actualSkdMeldingTrans1.getAdressenavn());
		assertEquals("adressetype",expectedSkdMeldingTrans1.getAdressetype(), actualSkdMeldingTrans1.getAdressetype());
		assertEquals("tilleggsadresse",expectedSkdMeldingTrans1.getTilleggsadresse(), actualSkdMeldingTrans1.getTilleggsadresse());
		assertEquals("postnummer",expectedSkdMeldingTrans1.getPostnummer(), actualSkdMeldingTrans1.getPostnummer());
		assertEquals("valgkrets",expectedSkdMeldingTrans1.getValgkrets(), actualSkdMeldingTrans1.getValgkrets());
		assertEquals("postadresse1",expectedSkdMeldingTrans1.getPostadresse1(), actualSkdMeldingTrans1.getPostadresse1());
		assertEquals("postadresse2",expectedSkdMeldingTrans1.getPostadresse2(), actualSkdMeldingTrans1.getPostadresse2());
		assertEquals("postadresse3",expectedSkdMeldingTrans1.getPostadresse3(), actualSkdMeldingTrans1.getPostadresse3());
		assertEquals("postadresseLand",expectedSkdMeldingTrans1.getPostadresseLand(), actualSkdMeldingTrans1.getPostadresseLand());
		assertEquals("innvandretFraLand",expectedSkdMeldingTrans1.getInnvandretFraLand(), actualSkdMeldingTrans1.getInnvandretFraLand());
		assertEquals("fraLandRegdato",expectedSkdMeldingTrans1.getFraLandRegdato(), actualSkdMeldingTrans1.getFraLandRegdato());
		assertEquals("fraLandFlyttedato",expectedSkdMeldingTrans1.getFraLandFlyttedato(), actualSkdMeldingTrans1.getFraLandFlyttedato());
		assertEquals("fraKommune",expectedSkdMeldingTrans1.getFraKommune(), actualSkdMeldingTrans1.getFraKommune());
		assertEquals("fraKommRegdato",expectedSkdMeldingTrans1.getFraKommRegdato(), actualSkdMeldingTrans1.getFraKommRegdato());
		assertEquals("fraKommFlyttedato",expectedSkdMeldingTrans1.getFraKommFlyttedato(), actualSkdMeldingTrans1.getFraKommFlyttedato());
		assertEquals("utvandretTilLand",expectedSkdMeldingTrans1.getUtvandretTilLand(), actualSkdMeldingTrans1.getUtvandretTilLand());
		assertEquals("tilLandRegdato",expectedSkdMeldingTrans1.getTilLandRegdato(), actualSkdMeldingTrans1.getTilLandRegdato());
		assertEquals("tilLandFlyttedato",expectedSkdMeldingTrans1.getTilLandFlyttedato(), actualSkdMeldingTrans1.getTilLandFlyttedato());
		assertEquals("samemanntall",expectedSkdMeldingTrans1.getSamemanntall(), actualSkdMeldingTrans1.getSamemanntall());
		assertEquals("datoSamemanntall",expectedSkdMeldingTrans1.getDatoSamemanntall(), actualSkdMeldingTrans1.getDatoSamemanntall());
		assertEquals("umyndiggjort",expectedSkdMeldingTrans1.getUmyndiggjort(), actualSkdMeldingTrans1.getUmyndiggjort());
		assertEquals("datoUmyndiggjort",expectedSkdMeldingTrans1.getDatoUmyndiggjort(), actualSkdMeldingTrans1.getDatoUmyndiggjort());
		assertEquals("foreldreansvar",expectedSkdMeldingTrans1.getForeldreansvar(), actualSkdMeldingTrans1.getForeldreansvar());
		assertEquals("datoForeldreansvar",expectedSkdMeldingTrans1.getDatoForeldreansvar(), actualSkdMeldingTrans1.getDatoForeldreansvar());
		assertEquals("arbeidstillatelse",expectedSkdMeldingTrans1.getArbeidstillatelse(), actualSkdMeldingTrans1.getArbeidstillatelse());
		assertEquals("datoArbeidstillatelse",expectedSkdMeldingTrans1.getDatoArbeidstillatelse(), actualSkdMeldingTrans1.getDatoArbeidstillatelse());
		assertEquals("fremkonnummer",expectedSkdMeldingTrans1.getFremkonnummer(), actualSkdMeldingTrans1.getFremkonnummer());
		assertEquals("morsFodselsdato",expectedSkdMeldingTrans1.getMorsFodselsdato(), actualSkdMeldingTrans1.getMorsFodselsdato());
		assertEquals("morsPersonnummer",expectedSkdMeldingTrans1.getMorsPersonnummer(), actualSkdMeldingTrans1.getMorsPersonnummer());
		assertEquals("morsNavn",expectedSkdMeldingTrans1.getMorsNavn(), actualSkdMeldingTrans1.getMorsNavn());
		assertEquals("morsStatsbSkap",expectedSkdMeldingTrans1.getMorsStatsbSkap(), actualSkdMeldingTrans1.getMorsStatsbSkap());
		assertEquals("farsFodselsdato",expectedSkdMeldingTrans1.getFarsFodselsdato(), actualSkdMeldingTrans1.getFarsFodselsdato());
		assertEquals("farsPersonnummer",expectedSkdMeldingTrans1.getFarsPersonnummer(), actualSkdMeldingTrans1.getFarsPersonnummer());
		assertEquals("farsFarsNavn",expectedSkdMeldingTrans1.getFarsFarsNavn(), actualSkdMeldingTrans1.getFarsFarsNavn());
		assertEquals("farsStatsbSkap",expectedSkdMeldingTrans1.getFarsStatsbSkap(), actualSkdMeldingTrans1.getFarsStatsbSkap());
		assertEquals("tidligereFnrDnr",expectedSkdMeldingTrans1.getTidligereFnrDnr(), actualSkdMeldingTrans1.getTidligereFnrDnr());
		assertEquals("datoTidlFnrDnr",expectedSkdMeldingTrans1.getDatoTidlFnrDnr(), actualSkdMeldingTrans1.getDatoTidlFnrDnr());
		assertEquals("nyttFnr",expectedSkdMeldingTrans1.getNyttFnr(), actualSkdMeldingTrans1.getNyttFnr());
		assertEquals("datoNyttFnr",expectedSkdMeldingTrans1.getDatoNyttFnr(), actualSkdMeldingTrans1.getDatoNyttFnr());
		assertEquals("levendeDoed",expectedSkdMeldingTrans1.getLevendeDoed(), actualSkdMeldingTrans1.getLevendeDoed());
		assertEquals("kjonn",expectedSkdMeldingTrans1.getKjonn(), actualSkdMeldingTrans1.getKjonn());
		assertEquals("tildelingskode",expectedSkdMeldingTrans1.getTildelingskode(), actualSkdMeldingTrans1.getTildelingskode());
		assertEquals("foedselstype",expectedSkdMeldingTrans1.getFoedselstype(), actualSkdMeldingTrans1.getFoedselstype());
		assertEquals("morsSiviltilstand",expectedSkdMeldingTrans1.getMorsSiviltilstand(), actualSkdMeldingTrans1.getMorsSiviltilstand());
		assertEquals("ekteskapPartnerskapNr",expectedSkdMeldingTrans1.getEkteskapPartnerskapNr(), actualSkdMeldingTrans1.getEkteskapPartnerskapNr());
		assertEquals("ektefelleEkteskapPartnerskapNr",expectedSkdMeldingTrans1.getEktefelleEkteskapPartnerskapNr(), actualSkdMeldingTrans1.getEktefelleEkteskapPartnerskapNr());
		assertEquals("vigselstype",expectedSkdMeldingTrans1.getVigselstype(), actualSkdMeldingTrans1.getVigselstype());
		assertEquals("forsByrde",expectedSkdMeldingTrans1.getForsByrde(), actualSkdMeldingTrans1.getForsByrde());
		assertEquals("dombevilling",expectedSkdMeldingTrans1.getDombevilling(), actualSkdMeldingTrans1.getDombevilling());
		assertEquals("antallBarn",expectedSkdMeldingTrans1.getAntallBarn(), actualSkdMeldingTrans1.getAntallBarn());
		assertEquals("tidligereSivilstand",expectedSkdMeldingTrans1.getTidligereSivilstand(), actualSkdMeldingTrans1.getTidligereSivilstand());
		assertEquals("ektefelleTidligereSivilstand",expectedSkdMeldingTrans1.getEktefelleTidligereSivilstand(), actualSkdMeldingTrans1.getEktefelleTidligereSivilstand());
		assertEquals("hjemmel",expectedSkdMeldingTrans1.getHjemmel(), actualSkdMeldingTrans1.getHjemmel());
		assertEquals("fylke",expectedSkdMeldingTrans1.getFylke(), actualSkdMeldingTrans1.getFylke());
		assertEquals("vigselskommune",expectedSkdMeldingTrans1.getVigselskommune(), actualSkdMeldingTrans1.getVigselskommune());
		assertEquals("tidlSepDomBev",expectedSkdMeldingTrans1.getTidlSepDomBev(), actualSkdMeldingTrans1.getTidlSepDomBev());
		assertEquals("begjertAv",expectedSkdMeldingTrans1.getBegjertAv(), actualSkdMeldingTrans1.getBegjertAv());
		assertEquals("registrGrunnlag",expectedSkdMeldingTrans1.getRegistrGrunnlag(), actualSkdMeldingTrans1.getRegistrGrunnlag());
		assertEquals("doedssted",expectedSkdMeldingTrans1.getDoedssted(), actualSkdMeldingTrans1.getDoedssted());
		assertEquals("typeDoedssted",expectedSkdMeldingTrans1.getTypeDoedssted(), actualSkdMeldingTrans1.getTypeDoedssted());
		assertEquals("vigselsdato",expectedSkdMeldingTrans1.getVigselsdato(), actualSkdMeldingTrans1.getVigselsdato());
		assertEquals("medlKirken",expectedSkdMeldingTrans1.getMedlKirken(), actualSkdMeldingTrans1.getMedlKirken());
		assertEquals("sekvensnr",expectedSkdMeldingTrans1.getSekvensnr(), actualSkdMeldingTrans1.getSekvensnr());
		assertEquals("bolignr",expectedSkdMeldingTrans1.getBolignr(), actualSkdMeldingTrans1.getBolignr());
		assertEquals("dufId",expectedSkdMeldingTrans1.getDufId(), actualSkdMeldingTrans1.getDufId());
		assertEquals("brukerident",expectedSkdMeldingTrans1.getBrukerident(), actualSkdMeldingTrans1.getBrukerident());
		assertEquals("skolerets",expectedSkdMeldingTrans1.getSkolerets(), actualSkdMeldingTrans1.getSkolerets());
		assertEquals("tkNr",expectedSkdMeldingTrans1.getTkNr(), actualSkdMeldingTrans1.getTkNr());
		assertEquals("dnrHjemlandsadresse1",expectedSkdMeldingTrans1.getDnrHjemlandsadresse1(), actualSkdMeldingTrans1.getDnrHjemlandsadresse1());
		assertEquals("dnrHjemlandsadresse2",expectedSkdMeldingTrans1.getDnrHjemlandsadresse2(), actualSkdMeldingTrans1.getDnrHjemlandsadresse2());
		assertEquals("dnrHjemlandsadresse3",expectedSkdMeldingTrans1.getDnrHjemlandsadresse3(), actualSkdMeldingTrans1.getDnrHjemlandsadresse3());
		assertEquals("dnrHjemlandLandkode",expectedSkdMeldingTrans1.getDnrHjemlandLandkode(), actualSkdMeldingTrans1.getDnrHjemlandLandkode());
		assertEquals("dnrHjemlandRegDato",expectedSkdMeldingTrans1.getDnrHjemlandRegDato(), actualSkdMeldingTrans1.getDnrHjemlandRegDato());
		assertEquals("dnrIdKontroll",expectedSkdMeldingTrans1.getDnrIdKontroll(), actualSkdMeldingTrans1.getDnrIdKontroll());
		assertEquals("postadrRegDato",expectedSkdMeldingTrans1.getPostadrRegDato(), actualSkdMeldingTrans1.getPostadrRegDato());
		assertEquals("utvandringstype",expectedSkdMeldingTrans1.getUtvandringstype(), actualSkdMeldingTrans1.getUtvandringstype());
		assertEquals("grunnkrets",expectedSkdMeldingTrans1.getGrunnkrets(), actualSkdMeldingTrans1.getGrunnkrets());
		assertEquals("statsborgerskap2",expectedSkdMeldingTrans1.getStatsborgerskap2(), actualSkdMeldingTrans1.getStatsborgerskap2());
		assertEquals("regdatoStatsb2",expectedSkdMeldingTrans1.getRegdatoStatsb2(), actualSkdMeldingTrans1.getRegdatoStatsb2());
		assertEquals("statsborgerskap3",expectedSkdMeldingTrans1.getStatsborgerskap3(), actualSkdMeldingTrans1.getStatsborgerskap3());
		assertEquals("regdatoStatsb3",expectedSkdMeldingTrans1.getRegdatoStatsb3(), actualSkdMeldingTrans1.getRegdatoStatsb3());
		assertEquals("statsborgerskap4",expectedSkdMeldingTrans1.getStatsborgerskap4(), actualSkdMeldingTrans1.getStatsborgerskap4());
		assertEquals("regdatoStatsb4",expectedSkdMeldingTrans1.getRegdatoStatsb4(), actualSkdMeldingTrans1.getRegdatoStatsb4());
		assertEquals("statsborgerskap5",expectedSkdMeldingTrans1.getStatsborgerskap5(), actualSkdMeldingTrans1.getStatsborgerskap5());
		assertEquals("regdatoStatsb5",expectedSkdMeldingTrans1.getRegdatoStatsb5(), actualSkdMeldingTrans1.getRegdatoStatsb5());
		assertEquals("statsborgerskap6",expectedSkdMeldingTrans1.getStatsborgerskap6(), actualSkdMeldingTrans1.getStatsborgerskap6());
		assertEquals("regdatoStatsb6",expectedSkdMeldingTrans1.getRegdatoStatsb6(), actualSkdMeldingTrans1.getRegdatoStatsb6());
		assertEquals("statsborgerskap7",expectedSkdMeldingTrans1.getStatsborgerskap7(), actualSkdMeldingTrans1.getStatsborgerskap7());
		assertEquals("regdatoStatsb7",expectedSkdMeldingTrans1.getRegdatoStatsb7(), actualSkdMeldingTrans1.getRegdatoStatsb7());
		assertEquals("statsborgerskap8",expectedSkdMeldingTrans1.getStatsborgerskap8(), actualSkdMeldingTrans1.getStatsborgerskap8());
		assertEquals("regdatoStatsb8",expectedSkdMeldingTrans1.getRegdatoStatsb8(), actualSkdMeldingTrans1.getRegdatoStatsb8());
		assertEquals("statsborgerskap9",expectedSkdMeldingTrans1.getStatsborgerskap9(), actualSkdMeldingTrans1.getStatsborgerskap9());
		assertEquals("regdatoStatsb9",expectedSkdMeldingTrans1.getRegdatoStatsb9(), actualSkdMeldingTrans1.getRegdatoStatsb9());
		assertEquals("statsborgerskap10",expectedSkdMeldingTrans1.getStatsborgerskap10(), actualSkdMeldingTrans1.getStatsborgerskap10());
		assertEquals("regdatoStatsb10",expectedSkdMeldingTrans1.getRegdatoStatsb10(), actualSkdMeldingTrans1.getRegdatoStatsb10());
		assertEquals("bibehold",expectedSkdMeldingTrans1.getBibehold(), actualSkdMeldingTrans1.getBibehold());
		assertEquals("regdatoBibehold",expectedSkdMeldingTrans1.getRegdatoBibehold(), actualSkdMeldingTrans1.getRegdatoBibehold());
		assertEquals("saksid",expectedSkdMeldingTrans1.getSaksid(), actualSkdMeldingTrans1.getSaksid());
		assertEquals("embete",expectedSkdMeldingTrans1.getEmbete(), actualSkdMeldingTrans1.getEmbete());
		assertEquals("sakstype",expectedSkdMeldingTrans1.getSakstype(), actualSkdMeldingTrans1.getSakstype());
		assertEquals("vedtaksdato",expectedSkdMeldingTrans1.getVedtaksdato(), actualSkdMeldingTrans1.getVedtaksdato());
		assertEquals("internVergeid",expectedSkdMeldingTrans1.getInternVergeid(), actualSkdMeldingTrans1.getInternVergeid());
		assertEquals("vergeFnrDnr",expectedSkdMeldingTrans1.getVergeFnrDnr(), actualSkdMeldingTrans1.getVergeFnrDnr());
		assertEquals("vergetype",expectedSkdMeldingTrans1.getVergetype(), actualSkdMeldingTrans1.getVergetype());
		assertEquals("mandattype",expectedSkdMeldingTrans1.getMandattype(), actualSkdMeldingTrans1.getMandattype());
		assertEquals("mandatTekst",expectedSkdMeldingTrans1.getMandatTekst(), actualSkdMeldingTrans1.getMandatTekst());
		assertEquals("reserverFramtidigBruk",expectedSkdMeldingTrans1.getReserverFramtidigBruk(), actualSkdMeldingTrans1.getReserverFramtidigBruk());
	}
}
