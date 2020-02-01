package bg.sofia.uni.fmi.mjt.authentication.model.web.request;

import java.util.UUID;

public interface PasswordChange {
    UUID getSessionId();
    String getUsername();
    String getOldPassword();
    String getNewPassword();
}
