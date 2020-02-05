package bg.sofia.uni.fmi.mjt.authentication.server.utils;

public interface PasswordEncryptor {

    String hashPassword(String password);
}
