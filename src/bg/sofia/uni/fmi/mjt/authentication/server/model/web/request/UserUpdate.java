package bg.sofia.uni.fmi.mjt.authentication.server.model.web.request;

import java.util.UUID;

public interface UserUpdate {
    UUID getSessionId();

    String getUsername();

    String getFirstName();

    String getLastName();

    String getEmail();
}
