package bg.sofia.uni.fmi.mjt.authentication.model;

import java.util.Objects;
import java.util.UUID;

public class Session {

    private UUID sessionId;
    private long ttl;

    public Session(long ttl){
        this.sessionId = UUID.randomUUID();
        this.ttl = ttl;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public long getTtl() {
        return ttl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return sessionId.equals(session.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }
}
