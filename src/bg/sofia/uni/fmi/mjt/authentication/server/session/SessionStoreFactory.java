package bg.sofia.uni.fmi.mjt.authentication.server.session;

public interface SessionStoreFactory {
    long DEFAULT_TTL = 5*60*1000L;
    static SessionStore getInstance(long ttl) {
        return new SessionStoreImpl(ttl);
    }
    static SessionStore getInstance() {
        return new SessionStoreImpl(DEFAULT_TTL);
    }
}
