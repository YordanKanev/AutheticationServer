package bg.sofia.uni.fmi.mjt.authentication.server.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class BasicPasswordEncryptor implements PasswordEncryptor {

    public static final String PASSWORD_NULL_EXCEPTION_MESSAGE = "Passoword is null.";

    private static BasicPasswordEncryptor basicPasswordEncryptor = null;
    private MessageDigest messageDigest;
    private byte[] salt = "secret".getBytes();

    private BasicPasswordEncryptor() throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("SHA-512");
    }

    public String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException(PASSWORD_NULL_EXCEPTION_MESSAGE);
        }
        messageDigest.reset();
        messageDigest.update(salt);
        return new String(messageDigest.digest(password.getBytes()));
    }

    public static BasicPasswordEncryptor getInstance() {
        if (basicPasswordEncryptor == null) {
            try {
                basicPasswordEncryptor = new BasicPasswordEncryptor();
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        }
        return basicPasswordEncryptor;
    }
}
