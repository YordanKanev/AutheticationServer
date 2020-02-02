package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Login;
import bg.sofia.uni.fmi.mjt.authentication.session.SessionStore;

public interface LoginFactory {

    static Login getInstance(UserRepository userRepository, SessionStore sessionStore) {
        return new LoginImpl(userRepository, sessionStore);
    }
}
