package bg.sofia.uni.fmi.mjt.authentication.server.audit;

import java.time.LocalDateTime;

public abstract class BasicEntry implements Entry {

    public static final String ENTRY_TYPE_NULL_EXCEPTION_MESSAGE = "EntryType is null.";
    public static final String ISSUER_NULL_EXCEPTION_MESSAGE = "Issuer is null.";

    private LocalDateTime timestamp;
    private EntryType entryType;
    private Issuer issuer;

    protected BasicEntry(EntryType entryType, Issuer issuer){
        if(entryType == null){
            throw new IllegalArgumentException(ENTRY_TYPE_NULL_EXCEPTION_MESSAGE);
        }
        if(issuer == null){
            throw new IllegalArgumentException(ISSUER_NULL_EXCEPTION_MESSAGE);
        }
        this.timestamp = LocalDateTime.now();
        this.entryType = entryType;
        this.issuer = issuer;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public EntryType getEntryType() {
        return entryType;
    }

    @Override
    public Issuer getIssuer() {
        return issuer;
    }
}
