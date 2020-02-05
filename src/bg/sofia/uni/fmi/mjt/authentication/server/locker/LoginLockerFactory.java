package bg.sofia.uni.fmi.mjt.authentication.server.locker;

import bg.sofia.uni.fmi.mjt.authentication.server.AuthenticationServerConfiguration;

public interface LoginLockerFactory {
    static LoginLocker getInstance(AuthenticationServerConfiguration authenticationServerConfiguration) {
        return new LoginLockerImpl(authenticationServerConfiguration);
    }
}
