package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.server.session.Session;

import java.util.UUID;

public interface Logout {
    Session logout(UUID sessionId);
}
