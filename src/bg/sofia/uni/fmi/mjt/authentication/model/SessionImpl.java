package bg.sofia.uni.fmi.mjt.authentication.model;

import bg.sofia.uni.fmi.mjt.authentication.model.user.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class SessionImpl implements Session {

    private UUID sessionId;
    private String username;

    public SessionImpl(String username){
        if(username == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        this.username = username;
        this.sessionId = UUID.randomUUID();
    }

    @Override
    public UUID getSessionId() {
        return sessionId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionImpl session = (SessionImpl) o;
        return sessionId.equals(session.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }
}
