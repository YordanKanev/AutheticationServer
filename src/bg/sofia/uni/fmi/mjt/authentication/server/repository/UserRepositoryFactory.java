package bg.sofia.uni.fmi.mjt.authentication.server.repository;

import java.io.IOException;

public interface UserRepositoryFactory {

    static UserRepository getInstance() throws IOException {
        return new UserRepositoryImpl();
    }
}
