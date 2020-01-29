package bg.sofia.uni.fmi.mjt.authentication.model.user;

import bg.sofia.uni.fmi.mjt.authentication.model.Session;

public interface SessionFactory {

    long DEFAULT_TTL = 300000L;

    static Session getInstance(){
        return getInstance(DEFAULT_TTL);
    }

    static Session getInstance(long ttl) {
        return new Session(ttl);
    }
}
