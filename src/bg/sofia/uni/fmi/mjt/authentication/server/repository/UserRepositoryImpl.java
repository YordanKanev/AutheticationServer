package bg.sofia.uni.fmi.mjt.authentication.server.repository;

import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.server.utils.PasswordEncryptor;
import bg.sofia.uni.fmi.mjt.authentication.server.utils.PasswordEncryptorInterfaceAdapter;
import bg.sofia.uni.fmi.mjt.authentication.server.utils.UserInterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;

class UserRepositoryImpl implements UserRepository {

    public static final String USER_NULL_EXCEPTION_MESSAGE = "User is null";
    public static final String USERNAME_NULL_EXCEPTION_MESSAGE = "Username is null";

    private static final String DATA_FILE_EXTENSION = ".dat";
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(User.class, new UserInterfaceAdapter())
            .registerTypeAdapter(PasswordEncryptor.class, new PasswordEncryptorInterfaceAdapter())
            .create();

    @Override
    public User delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException(USER_NULL_EXCEPTION_MESSAGE);
        }
        String username = user.getUsername();
        if (!exists(username)) {
            return null;
        }
        Path filePath = getFilePath(username);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    @Override
    public boolean exists(String username) {
        if (username == null) {
            throw new IllegalArgumentException(USERNAME_NULL_EXCEPTION_MESSAGE);
        }
        Path filePath = getFilePath(username);
        return Files.exists(filePath);
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findOne(String username) {
        if (username == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        if (!exists(username)) {
            return null;
        }
        Path filePath = getFilePath(username);
        try {
            Reader reader = new FileReader(filePath.toString());
            Type userType = new TypeToken<User>() {}.getType();
            User user = GSON.fromJson(reader, userType);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T extends User> T save(T user) {
        if (user == null) {
            throw new IllegalArgumentException(USER_NULL_EXCEPTION_MESSAGE);
        }
        String json = GSON.toJson(user, User.class);
        String username = user.getUsername();
        Path filePath = getFilePath(username);
        try {
            Files.write(filePath, json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    @Override
    public Iterable<User> findAll() {
        throw new UnsupportedOperationException();
    }

    private static String formatFileName(String username) {
        return username + DATA_FILE_EXTENSION;
    }

    private static Path getFilePath(String username) {
        String fileName = formatFileName(username);
        return Path.of(fileName);
    }
}
