package bg.sofia.uni.fmi.mjt.authentication.server.model.user;

import bg.sofia.uni.fmi.mjt.authentication.server.utils.PasswordEncryptorFactory;

public interface UserFactory {

    static User getInstance(String username, String firstName, String lastName,
                            String email, String password) {
        return new BasicUser(username
                , password
                , firstName
                , lastName
                , email
                , false
                , PasswordEncryptorFactory.getInstance());
    }
}
