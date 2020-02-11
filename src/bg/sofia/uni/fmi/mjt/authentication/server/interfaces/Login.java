package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import java.util.UUID;

public interface Login {
    UUID login(UUID sessionId);

    UUID login(String username, String password);

}
