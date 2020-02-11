package bg.sofia.uni.fmi.mjt.authentication.server;

import java.nio.file.Path;

public interface AuthenticationServerConfiguration {

    long DEFAULT_LOCK_TIME = 10;//seconds
    int DEFAULT_LOGIN_ATTEMPTS_COUNT = 3;

    long getLockTime();

    int getLoginAttemptsCount();

    static AuthenticationServerConfiguration defaultConfiguration() {
        return new AuthenticationServerConfiguration() {
            @Override
            public long getLockTime() {
                return DEFAULT_LOCK_TIME;
            }

            @Override
            public int getLoginAttemptsCount() {
                return DEFAULT_LOGIN_ATTEMPTS_COUNT;
            }
        };
    }
}
