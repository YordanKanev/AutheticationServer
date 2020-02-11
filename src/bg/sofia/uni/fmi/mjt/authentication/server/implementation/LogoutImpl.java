package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Logout;
import bg.sofia.uni.fmi.mjt.authentication.server.session.Session;
import bg.sofia.uni.fmi.mjt.authentication.server.session.SessionStore;

import java.util.UUID;

public class LogoutImpl implements Logout {

    private SessionStore sessionStore;

    public LogoutImpl(SessionStore sessionStore) {
        if (sessionStore == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        this.sessionStore = sessionStore;
    }

    @Override
    public Session logout(UUID sessionId) {
        if (sessionId == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        return sessionStore.deleteSession(sessionId);
    }
}
