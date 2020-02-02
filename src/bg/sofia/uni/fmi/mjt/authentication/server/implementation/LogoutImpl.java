package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Logout;
import bg.sofia.uni.fmi.mjt.authentication.session.Session;
import bg.sofia.uni.fmi.mjt.authentication.session.SessionStore;

import java.util.UUID;

public class LogoutImpl implements Logout {

    private SessionStore sessionStore;

    public LogoutImpl(SessionStore sessionStore) {
        if(sessionStore == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        this.sessionStore = sessionStore;
    }

    @Override
    public Session logout(UUID sessionId) {
        if(sessionId == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        return sessionStore.deleteSession(sessionId);
    }
}
