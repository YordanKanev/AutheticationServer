package bg.sofia.uni.fmi.mjt.authentication.audit;

public class FailedLoginEntry extends BasicEntry {
    public FailedLoginEntry(Issuer issuer) {
        super(EntryType.FAILED_LOGIN, issuer);
    }
}
