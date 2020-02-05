package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Login;
import bg.sofia.uni.fmi.mjt.authentication.session.SessionStore;

import java.util.UUID;

class LoginImpl implements Login {

    private UserRepository userRepository;
    private SessionStore sessionStore;

    public LoginImpl(UserRepository userRepository, SessionStore sessionStore) {
        if (userRepository == null || sessionStore == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        this.userRepository = userRepository;
        this.sessionStore = sessionStore;
    }

    @Override
    public UUID login(UUID sessionId) {
        if(sessionId == null) {
            throw new IllegalArgumentException();
        }
        return sessionStore.refreshSession(sessionId);
    }

    @Override
    public UUID login(String username, String password) {
        if(username == null || password == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }

        if(userRepository.exists(username)){
            User user = userRepository.findOne(username);
            if(user == null){
                return  null;
            }
            if(user.verifyPassword(password)){
                UUID sessionId = sessionStore.refreshSession(username);
                if(sessionId == null){
                    return sessionStore.createSession(username);
                }
            }
        }
        return null;
    }
}
