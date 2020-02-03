package bg.sofia.uni.fmi.mjt.authentication.commands;

import java.util.UUID;

public interface Secured {

    String INVALID_SESSION_ID_MESSAGE = "The provided sessionId is invalid.";

    UUID getSessionId();
}
