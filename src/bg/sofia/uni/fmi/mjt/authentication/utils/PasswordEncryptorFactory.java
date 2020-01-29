package bg.sofia.uni.fmi.mjt.authentication.utils;

public interface PasswordEncryptorFactory {

    static PasswordEncryptor getInstance(){
        return BasicPasswordEncryptor.getInstance();
    }
}
