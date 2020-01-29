package bg.sofia.uni.fmi.mjt.authentication.audit;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Entry extends Serializable {
    LocalDateTime getTimestamp();
    EntryType getEntryType();
    Issuer getIssuer();
}
