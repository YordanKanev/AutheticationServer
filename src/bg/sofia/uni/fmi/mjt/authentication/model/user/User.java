package bg.sofia.uni.fmi.mjt.authentication.model.user;

import java.io.Serializable;

public interface User extends Serializable {

    String USERNAME_NULL_EXCEPTION_MESSAGE = "Username is null.";
    String FIRSTNAME_NULL_EXCEPTION_MESSAGE = "FirstName is null.";
    String LASTNAME_NULL_EXCEPTION_MESSAGE = "LastName is null.";
    String EMAIL_NULL_EXCEPTION_MESSAGE = "Email is null.";
    String PASSWORD_NULL_EXCEPTION_MESSAGE = "Password is null.";
    String PASSWORDENCRYPTOR_NULL_EXCEPTION_MESSAGE = "PasswordEncryptor is null.";
    String PASSWORD_MISMATCH_MESSAGE = "Wrong password.";

    String getUsername();
    String getFirstName();
    String getLastName();
    String getEmail();
    void setUsername(String username);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setEmail(String email);
    void setPassword(String password);
    void setAdmin(boolean admin);

    boolean isAdmin();
    boolean verifyPassword(String password);

}
