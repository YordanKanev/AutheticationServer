package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;
import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Login;
import bg.sofia.uni.fmi.mjt.authentication.server.session.SessionStore;

import java.util.UUID;

class LoginImpl implements Login {

    private UserRepository userRepository;
    private SessionStore sessionStore;

    public LoginImpl(UserRepository userRepository, SessionStore sessionStore) {
        if (userRepository == null || sessionStore == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        this.userRepository = userRepository;
        this.sessionStore = sessionStore;
    }

    @Override
    public UUID login(UUID sessionId) {
        if(sessionId == null) {
            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        return sessionStore.refreshSession(sessionId);
    }

    @Override
    public UUID login(String username, String password) {
        if(username == null || password == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }

        if(userRepository.exists(username)){
            User user = userRepository.findOne(username);
            if(user == null){
                return  null;
            }
            if(user.verifyPassword(password)){
                UUID sessionId = sessionStore.refreshSession(username);
                if(sessionId == null){
                    sessionId = sessionStore.createSession(username);
                }
                return sessionId;
            }
        }
        return null;
    }
}
