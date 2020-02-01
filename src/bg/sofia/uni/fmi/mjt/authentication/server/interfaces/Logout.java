package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import java.util.UUID;

public interface Logout {
    void logout(UUID sessionId);
}
