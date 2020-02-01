package bg.sofia.uni.fmi.mjt.authentication.session;

public interface SessionFactory {

    static Session getInstance(String username) {
        return new SessionImpl(username);
    }
}
