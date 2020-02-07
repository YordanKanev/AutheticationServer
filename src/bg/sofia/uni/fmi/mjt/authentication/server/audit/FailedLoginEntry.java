package bg.sofia.uni.fmi.mjt.authentication.server.audit;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer.Issuer;

public class FailedLoginEntry extends BasicEntry {
    public FailedLoginEntry(Issuer issuer) {
        super(EntryType.FAILED_LOGIN, issuer);
    }
}
