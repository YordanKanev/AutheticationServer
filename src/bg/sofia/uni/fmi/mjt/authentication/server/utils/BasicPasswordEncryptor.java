package bg.sofia.uni.fmi.mjt.authentication.server.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class BasicPasswordEncryptor implements PasswordEncryptor {

    public static final String PASSWORD_NULL_EXCEPTION_MESSAGE = "Passoword is null.";

    private static BasicPasswordEncryptor basicPasswordEncryptor = null;
    private static final int SALT_SIZE = 16;

    private MessageDigest messageDigest;

    private BasicPasswordEncryptor() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(salt);
    }

    public String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException(PASSWORD_NULL_EXCEPTION_MESSAGE);
        }
        return new String(messageDigest.digest(password.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8)
                .intern();
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
