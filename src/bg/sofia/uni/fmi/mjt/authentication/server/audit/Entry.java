package bg.sofia.uni.fmi.mjt.authentication.server.audit;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer.Issuer;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Entry extends Serializable {
    LocalDateTime getTimestamp();

    EntryType getEntryType();

    Issuer getIssuer();
}
