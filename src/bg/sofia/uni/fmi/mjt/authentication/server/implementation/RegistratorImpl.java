package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;
import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.server.model.user.UserFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.UserRegistration;
import bg.sofia.uni.fmi.mjt.authentication.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Registrator;

class RegistratorImpl implements Registrator {
    public static final String USER_ALREADY_EXIST_MESSAGE = "User already exist";

    private UserRepository userRepository;

    public RegistratorImpl(UserRepository userRepository){
        if(userRepository == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        this.userRepository = userRepository;
    }
    @Override
    public User register(UserRegistration userRegistration) {
        if(userRegistration == null){

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        if(userRepository.exists(userRegistration.getUsername())){
            throw new IllegalArgumentException(USER_ALREADY_EXIST_MESSAGE);
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
