package no.nav.tps.forvalteren.service.command.tps.skdmelding.skdparam.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/*
    Generert fra "Landkoder TPS FELLES og TSS 20170209.xlsx" - "TPS Felles"
 */
@Service
@SuppressWarnings("checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck")
public class StatsborgerskapEncoder {

    private static final Map<String, String> statsborgerskapMap = new HashMap<>();

    static {
        statsborgerskapMap.put("???", "990");
        statsborgerskapMap.put("ABW", "657");
        statsborgerskapMap.put("AFG", "404");
        statsborgerskapMap.put("AGO", "204");
        statsborgerskapMap.put("AIA", "660");
        statsborgerskapMap.put("ALA", "860");
        statsborgerskapMap.put("ALB", "111");
        statsborgerskapMap.put("AND", "114");
        statsborgerskapMap.put("ANT", "656");
        statsborgerskapMap.put("ARE", "426");
        statsborgerskapMap.put("ARG", "705");
        statsborgerskapMap.put("ARM", "406");
        statsborgerskapMap.put("ASM", "802");
        statsborgerskapMap.put("ATF", "628");
        statsborgerskapMap.put("ATG", "603");
        statsborgerskapMap.put("AUS", "805");
        statsborgerskapMap.put("AUT", "153");
        statsborgerskapMap.put("AZE", "407");
        statsborgerskapMap.put("BDI", "216");
        statsborgerskapMap.put("BEL", "112");
        statsborgerskapMap.put("BEN", "229");
        statsborgerskapMap.put("BFA", "393");
        statsborgerskapMap.put("BGD", "410");
        statsborgerskapMap.put("BGR", "113");
        statsborgerskapMap.put("BHR", "409");
        statsborgerskapMap.put("BHS", "605");
        statsborgerskapMap.put("BIH", "155");
        statsborgerskapMap.put("BLM", "687");
        statsborgerskapMap.put("BLR", "120");
        statsborgerskapMap.put("BLZ", "604");
        statsborgerskapMap.put("BMU", "606");
        statsborgerskapMap.put("BOL", "710");
        statsborgerskapMap.put("BRA", "715");
        statsborgerskapMap.put("BRB", "602");
        statsborgerskapMap.put("BRN", "416");
        statsborgerskapMap.put("BTN", "412");
        statsborgerskapMap.put("BVT", "875");
        statsborgerskapMap.put("BWA", "205");
        statsborgerskapMap.put("CAF", "337");
        statsborgerskapMap.put("CAN", "612");
        statsborgerskapMap.put("CCK", "808");
        statsborgerskapMap.put("CHE", "141");
        statsborgerskapMap.put("CHL", "725");
        statsborgerskapMap.put("CHN", "484");
        statsborgerskapMap.put("CIV", "239");
        statsborgerskapMap.put("CMR", "270");
        statsborgerskapMap.put("COD", "279");
        statsborgerskapMap.put("COG", "278");
        statsborgerskapMap.put("COK", "809");
        statsborgerskapMap.put("COL", "730");
        statsborgerskapMap.put("COM", "220");
        statsborgerskapMap.put("CPV", "273");
        statsborgerskapMap.put("CRI", "616");
        statsborgerskapMap.put("CSK", "142");
        statsborgerskapMap.put("CUB", "620");
        statsborgerskapMap.put("CXR", "807");
        statsborgerskapMap.put("CYM", "613");
        statsborgerskapMap.put("CYP", "500");
        statsborgerskapMap.put("CZE", "158");
        statsborgerskapMap.put("DDR", "151");
        statsborgerskapMap.put("DEU", "144");
        statsborgerskapMap.put("DJI", "250");
        statsborgerskapMap.put("DMA", "622");
        statsborgerskapMap.put("DNK", "101");
        statsborgerskapMap.put("DOM", "624");
        statsborgerskapMap.put("DZA", "203");
        statsborgerskapMap.put("ECU", "735");
        statsborgerskapMap.put("EGY", "249");
        statsborgerskapMap.put("ERI", "241");
        statsborgerskapMap.put("ESH", "304");
        statsborgerskapMap.put("ESP", "137");
        statsborgerskapMap.put("EST", "115");
        statsborgerskapMap.put("ETH", "246");
        statsborgerskapMap.put("FIN", "103");
        statsborgerskapMap.put("FJI", "811");
        statsborgerskapMap.put("FLK", "740");
        statsborgerskapMap.put("FRA", "117");
        statsborgerskapMap.put("FRO", "104");
        statsborgerskapMap.put("FSM", "826");
        statsborgerskapMap.put("GAB", "254");
        statsborgerskapMap.put("GBR", "139");
        statsborgerskapMap.put("GEO", "430");
        statsborgerskapMap.put("GGY", "162");
        statsborgerskapMap.put("GHA", "260");
        statsborgerskapMap.put("GIB", "118");
        statsborgerskapMap.put("GIN", "264");
        statsborgerskapMap.put("GLP", "631");
        statsborgerskapMap.put("GMB", "256");
        statsborgerskapMap.put("GNB", "266");
        statsborgerskapMap.put("GNQ", "235");
        statsborgerskapMap.put("GRC", "119");
        statsborgerskapMap.put("GRD", "629");
        statsborgerskapMap.put("GRL", "102");
        statsborgerskapMap.put("GTM", "632");
        statsborgerskapMap.put("GUF", "745");
        statsborgerskapMap.put("GUM", "817");
        statsborgerskapMap.put("GUY", "720");
        statsborgerskapMap.put("HKG", "436");
        statsborgerskapMap.put("HMD", "870");
        statsborgerskapMap.put("HND", "644");
        statsborgerskapMap.put("HRV", "122");
        statsborgerskapMap.put("HTI", "636");
        statsborgerskapMap.put("HUN", "152");
        statsborgerskapMap.put("IDN", "448");
        statsborgerskapMap.put("IMN", "164");
        statsborgerskapMap.put("IND", "444");
        statsborgerskapMap.put("IOT", "213");
        statsborgerskapMap.put("IRL", "121");
        statsborgerskapMap.put("IRN", "456");
        statsborgerskapMap.put("IRQ", "452");
        statsborgerskapMap.put("ISL", "105");
        statsborgerskapMap.put("ISR", "460");
        statsborgerskapMap.put("ITA", "123");
        statsborgerskapMap.put("JAM", "648");
        statsborgerskapMap.put("JOR", "476");
        statsborgerskapMap.put("JPN", "464");
        statsborgerskapMap.put("KAZ", "480");
        statsborgerskapMap.put("KEN", "276");
        statsborgerskapMap.put("KGZ", "502");
        statsborgerskapMap.put("KHM", "478");
        statsborgerskapMap.put("KIR", "815");
        statsborgerskapMap.put("KNA", "677");
        statsborgerskapMap.put("KOR", "492");
        statsborgerskapMap.put("KWT", "496");
        statsborgerskapMap.put("LAO", "504");
        statsborgerskapMap.put("LBN", "508");
        statsborgerskapMap.put("LBR", "283");
        statsborgerskapMap.put("LBY", "286");
        statsborgerskapMap.put("LCA", "678");
        statsborgerskapMap.put("LIE", "128");
        statsborgerskapMap.put("LKA", "424");
        statsborgerskapMap.put("LSO", "281");
        statsborgerskapMap.put("LTU", "136");
        statsborgerskapMap.put("LUX", "129");
        statsborgerskapMap.put("LVA", "124");
        statsborgerskapMap.put("MAC", "510");
        statsborgerskapMap.put("MAF", "686");
        statsborgerskapMap.put("MAR", "303");
        statsborgerskapMap.put("MCO", "130");
        statsborgerskapMap.put("MDA", "138");
        statsborgerskapMap.put("MDG", "289");
        statsborgerskapMap.put("MDV", "513");
        statsborgerskapMap.put("MEX", "652");
        statsborgerskapMap.put("MHL", "835");
        statsborgerskapMap.put("MKD", "156");
        statsborgerskapMap.put("MLI", "299");
        statsborgerskapMap.put("MLT", "126");
        statsborgerskapMap.put("MMR", "420");
        statsborgerskapMap.put("MNG", "516");
        statsborgerskapMap.put("MNP", "840");
        statsborgerskapMap.put("MOZ", "319");
        statsborgerskapMap.put("MRT", "306");
        statsborgerskapMap.put("MSR", "654");
        statsborgerskapMap.put("MTQ", "650");
        statsborgerskapMap.put("MUS", "307");
        statsborgerskapMap.put("MWI", "296");
        statsborgerskapMap.put("MYS", "512");
        statsborgerskapMap.put("MYT", "322");
        statsborgerskapMap.put("NAM", "308");
        statsborgerskapMap.put("NCL", "833");
        statsborgerskapMap.put("NER", "309");
        statsborgerskapMap.put("NFK", "822");
        statsborgerskapMap.put("NGA", "313");
        statsborgerskapMap.put("NIC", "664");
        statsborgerskapMap.put("NIU", "821");
        statsborgerskapMap.put("NLD", "127");
        statsborgerskapMap.put("NOR", "000");
        statsborgerskapMap.put("NPL", "528");
        statsborgerskapMap.put("NRU", "818");
        statsborgerskapMap.put("NZL", "820");
        statsborgerskapMap.put("OMN", "520");
        statsborgerskapMap.put("PAK", "534");
        statsborgerskapMap.put("PAN", "668");
        statsborgerskapMap.put("PCN", "828");
        statsborgerskapMap.put("PER", "760");
        statsborgerskapMap.put("PHL", "428");
        statsborgerskapMap.put("PLW", "839");
        statsborgerskapMap.put("PNG", "827");
        statsborgerskapMap.put("POL", "131");
        statsborgerskapMap.put("PRI", "685");
        statsborgerskapMap.put("PRK", "488");
        statsborgerskapMap.put("PRT", "132");
        statsborgerskapMap.put("PRY", "755");
        statsborgerskapMap.put("PYF", "814");
        statsborgerskapMap.put("QAT", "540");
        statsborgerskapMap.put("REU", "323");
        statsborgerskapMap.put("ROU", "133");
        statsborgerskapMap.put("RUS", "140");
        statsborgerskapMap.put("RWA", "329");
        statsborgerskapMap.put("SAU", "544");
        statsborgerskapMap.put("SDN", "356");
        statsborgerskapMap.put("SEN", "336");
        statsborgerskapMap.put("SGP", "548");
        statsborgerskapMap.put("SGS", "865");
        statsborgerskapMap.put("SHN", "209");
        statsborgerskapMap.put("SJM", "744");
        statsborgerskapMap.put("SLB", "806");
        statsborgerskapMap.put("SLE", "339");
        statsborgerskapMap.put("SLV", "672");
        statsborgerskapMap.put("SMR", "134");
        statsborgerskapMap.put("SOM", "346");
        statsborgerskapMap.put("SPM", "676");
        statsborgerskapMap.put("STP", "333");
        statsborgerskapMap.put("SUN", "135");
        statsborgerskapMap.put("SUR", "765");
        statsborgerskapMap.put("SVK", "157");
        statsborgerskapMap.put("SVN", "146");
        statsborgerskapMap.put("SWE", "106");
        statsborgerskapMap.put("SWZ", "357");
        statsborgerskapMap.put("SYC", "338");
        statsborgerskapMap.put("SYR", "564");
        statsborgerskapMap.put("TCA", "681");
        statsborgerskapMap.put("TCD", "373");
        statsborgerskapMap.put("TGO", "376");
        statsborgerskapMap.put("THA", "568");
        statsborgerskapMap.put("TJK", "550");
        statsborgerskapMap.put("TKL", "829");
        statsborgerskapMap.put("TKM", "552");
        statsborgerskapMap.put("TON", "813");
        statsborgerskapMap.put("TTO", "680");
        statsborgerskapMap.put("TUN", "379");
        statsborgerskapMap.put("TUR", "143");
        statsborgerskapMap.put("TUV", "816");
        statsborgerskapMap.put("TWN", "432");
        statsborgerskapMap.put("TZA", "369");
        statsborgerskapMap.put("UGA", "386");
        statsborgerskapMap.put("UKR", "148");
        statsborgerskapMap.put("UMI", "819");
        statsborgerskapMap.put("URY", "770");
        statsborgerskapMap.put("USA", "684");
        statsborgerskapMap.put("UZB", "554");
        statsborgerskapMap.put("VAT", "154");
        statsborgerskapMap.put("VCT", "679");
        statsborgerskapMap.put("VEN", "775");
        statsborgerskapMap.put("VGB", "608");
        statsborgerskapMap.put("VIR", "601");
        statsborgerskapMap.put("VNM", "575");
        statsborgerskapMap.put("VUT", "812");
        statsborgerskapMap.put("WAK", "831");
        statsborgerskapMap.put("WLF", "832");
        statsborgerskapMap.put("WSM", "830");
        statsborgerskapMap.put("XXK", "161");
        statsborgerskapMap.put("XXX", "980");
        statsborgerskapMap.put("YEM", "578");
        statsborgerskapMap.put("ZAF", "359");
        statsborgerskapMap.put("ZMB", "389");
        statsborgerskapMap.put("ZWE", "326");
        statsborgerskapMap.put("349", "349");
        statsborgerskapMap.put("546", "546");
        statsborgerskapMap.put("556", "556");
        statsborgerskapMap.put("669", "669");
        statsborgerskapMap.put("YUG", "925");
        statsborgerskapMap.put("PSE", "524");
        statsborgerskapMap.put("TLS", "537");
        statsborgerskapMap.put("SCG", "125");
        statsborgerskapMap.put("MNE", "160");
        statsborgerskapMap.put("SRB", "159");
        statsborgerskapMap.put("BES", "659");
        statsborgerskapMap.put("CUW", "661");
        statsborgerskapMap.put("JEY", "163");
        statsborgerskapMap.put("SSD", "355");
        statsborgerskapMap.put("SXM", "658");
    }

    public String encode(String statsborgerskap) {
        return statsborgerskapMap.getOrDefault(statsborgerskap, "000");
    }

}
