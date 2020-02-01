package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.model.web.request.PasswordChange;

public interface PasswordResetter {
    void resetPassword(PasswordChange passwordChange);
}
