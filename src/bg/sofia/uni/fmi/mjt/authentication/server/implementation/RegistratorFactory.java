package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Registrator;

public interface RegistratorFactory {

    static Registrator getInstance(UserRepository userRepository) {
        return new RegistratorImpl(userRepository);
    }
}
