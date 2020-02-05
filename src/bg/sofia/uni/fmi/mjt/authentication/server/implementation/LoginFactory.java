package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Login;
import bg.sofia.uni.fmi.mjt.authentication.server.session.SessionStore;

public interface LoginFactory {

    static Login getInstance(UserRepository userRepository,
                             SessionStore sessionStore) {
        return new LoginImpl(userRepository, sessionStore);
    }
}
