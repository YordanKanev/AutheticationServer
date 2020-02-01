package bg.sofia.uni.fmi.mjt.authentication.model;

import bg.sofia.uni.fmi.mjt.authentication.model.user.User;

import java.util.UUID;

public interface Session {

    UUID getSessionId();
    String getUsername();
}
