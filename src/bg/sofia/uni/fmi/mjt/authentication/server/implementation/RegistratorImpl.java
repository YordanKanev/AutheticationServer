package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.model.user.UserFactory;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.UserRegistration;
import bg.sofia.uni.fmi.mjt.authentication.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Login;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Registrator;

class RegistratorImpl implements Registrator {

    private UserRepository userRepository;

    public RegistratorImpl(UserRepository userRepository){
        if(userRepository == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        this.userRepository = userRepository;
    }
    @Override
    public User register(UserRegistration userRegistration) {
        if(userRegistration == null){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        if(userRepository.exists(userRegistration.getUsername())){
            //TODO: throw exception
        }
        User user = UserFactory.getInstance(userRegistration.getUsername(),
                userRegistration.getFirstName(),
                userRegistration.getLastName(),
                userRegistration.getEmail(),
                userRegistration.getPassword());
        userRepository.save(user);
        return user;
    }
}
