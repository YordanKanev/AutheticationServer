package bg.sofia.uni.fmi.mjt.authentication.model.user;

import bg.sofia.uni.fmi.mjt.authentication.model.user.BasicUser;
import bg.sofia.uni.fmi.mjt.authentication.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.utils.PasswordEncryptorFactory;

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
