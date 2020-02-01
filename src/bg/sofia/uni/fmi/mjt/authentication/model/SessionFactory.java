package bg.sofia.uni.fmi.mjt.authentication.model;

public interface SessionFactory {

    static Session getInstance(String username) {
        return new SessionImpl(username);
    }
}
