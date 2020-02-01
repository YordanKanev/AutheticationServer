package bg.sofia.uni.fmi.mjt.authentication.model;

import bg.sofia.uni.fmi.mjt.authentication.model.SessionImpl;
import bg.sofia.uni.fmi.mjt.authentication.model.user.User;

public interface SessionFactory {

    static SessionImpl getInstance(String username) {
        return new SessionImpl(username);
    }
}
