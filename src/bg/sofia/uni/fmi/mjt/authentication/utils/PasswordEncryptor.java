package bg.sofia.uni.fmi.mjt.authentication.utils;

public interface PasswordEncryptor {

    String hashPassword(String password);
}
