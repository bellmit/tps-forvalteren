package no.nav.tps.forvalteren.domain.rs.skd;

public enum DoedsmeldingHandlingType {

    C("CREATE -- Send dødsmelding"),
    U("UPDATE -- Endre dødsmelding"),
    D("DELETE -- Annuler");

    private String handling;

    public String getHandling() {
        return handling;
    }

    DoedsmeldingHandlingType(String handling) {
        this.handling = handling;
    }
}