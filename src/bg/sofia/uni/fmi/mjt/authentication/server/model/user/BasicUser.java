package bg.sofia.uni.fmi.mjt.authentication.server.model.user;

import bg.sofia.uni.fmi.mjt.authentication.server.utils.PasswordEncryptor;
import bg.sofia.uni.fmi.mjt.authentication.server.utils.PasswordEncryptorFactory;

import java.util.Objects;

class BasicUser implements User {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean admin;
    private transient PasswordEncryptor passwordEncryptor;

    public BasicUser(){
        this.passwordEncryptor = PasswordEncryptorFactory.getInstance();
    }
    public BasicUser(String username, String password,
                     String firstName, String lastName, String email,
                     boolean admin, PasswordEncryptor passwordEncryptor){
        setPasswordEncryptor(passwordEncryptor);
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setAdmin(admin);

    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setUsername(String username) {
        if(username == null){
            throw new IllegalArgumentException(USERNAME_NULL_EXCEPTION_MESSAGE);
        }
        this.username = username;
    }

    @Override
    public void setFirstName(String firstName) {
        if(firstName == null){
            throw new IllegalArgumentException(FIRSTNAME_NULL_EXCEPTION_MESSAGE);
        }
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        if(lastName == null){
            throw new IllegalArgumentException(LASTNAME_NULL_EXCEPTION_MESSAGE);
        }
        this.lastName = lastName;
    }

    @Override
    public void setEmail(String email) {
        if(email == null){
            throw new IllegalArgumentException(EMAIL_NULL_EXCEPTION_MESSAGE);
        }
        this.email = email;
    }

    @Override
    public void setPassword(String password) {
        if(password == null){
            throw new IllegalArgumentException(PASSWORD_NULL_EXCEPTION_MESSAGE);
        }
        this.password = passwordEncryptor.hashPassword(password);
    }

    @Override
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean isAdmin() {
        return admin;
    }

    @Override
    public boolean verifyPassword(String password) {
        return true;
        /*if(password == null){
            throw new IllegalArgumentException(PASSWORD_NULL_EXCEPTION_MESSAGE);
        }
        String hashed = passwordEncryptor.hashPassword(password);
        return hashed.equals(this.password);*/
    }

    private void setPasswordEncryptor(PasswordEncryptor passwordEncryptor){
        if(passwordEncryptor == null){
            throw new IllegalArgumentException(PASSWORDENCRYPTOR_NULL_EXCEPTION_MESSAGE);
        }
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicUser basicUser = (BasicUser) o;
        return username.equals(basicUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
