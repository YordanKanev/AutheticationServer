package bg.sofia.uni.fmi.mjt.authentication.server.utils;

public interface PasswordEncryptorFactory {

    static PasswordEncryptor getInstance() {
        return BasicPasswordEncryptor.getInstance();
    }
}
