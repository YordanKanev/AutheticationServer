package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Logout;
import bg.sofia.uni.fmi.mjt.authentication.server.session.SessionStore;

public interface LogoutFactory {
    static Logout getInstance(SessionStore sessionStore) {
        return new LogoutImpl(sessionStore);
    }
}
