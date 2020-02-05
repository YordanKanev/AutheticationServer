package bg.sofia.uni.fmi.mjt.authentication.server.session;

import java.util.UUID;

public interface Session {

    UUID getSessionId();
    String getUsername();
}
