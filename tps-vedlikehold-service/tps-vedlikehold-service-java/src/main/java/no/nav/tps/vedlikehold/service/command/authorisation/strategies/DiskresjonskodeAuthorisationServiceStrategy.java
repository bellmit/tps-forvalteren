package no.nav.tps.vedlikehold.service.command.authorisation.strategies;

import no.nav.tps.vedlikehold.consumer.ws.tpsws.diskresjonskode.DiskresjonskodeConsumer;
import no.nav.tps.vedlikehold.domain.service.User;

/**
 * @author Øyvind Grimnes, Visma Consulting AS
 */
public class DiskresjonskodeAuthorisationServiceStrategy implements AuthorisationServiceStrategy {

    private static final String ROLE_READ_DISKRESJONSKODE_6 = "0000-GA-GOSYS_KODE6";
    private static final String ROLE_READ_DISKRESJONSKODE_7 = "0000-GA-GOSYS_KODE7";

    private User user;
    private String fnr;
    private DiskresjonskodeConsumer diskresjonskodeConsumer;


    @Override
    public Boolean isAuthorised() {
        String diskresjonskode;

        try {
            diskresjonskode = diskresjonskodeConsumer.getDiskresjonskode(fnr).getDiskresjonskode();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        if (diskresjonskode.equals("6")) {
            return user.getRoles().contains(ROLE_READ_DISKRESJONSKODE_6);
        }

        return !diskresjonskode.equals("7") || user.getRoles().contains(ROLE_READ_DISKRESJONSKODE_7);
    }

    /* Getters and setters */

    public DiskresjonskodeConsumer getDiskresjonskodeConsumer() {
        return diskresjonskodeConsumer;
    }

    public void setDiskresjonskodeConsumer(DiskresjonskodeConsumer diskresjonskodeConsumer) {
        this.diskresjonskodeConsumer = diskresjonskodeConsumer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFnr() {
        return fnr;
    }

    public void setFnr(String fnr) {
        this.fnr = fnr;
    }
}
