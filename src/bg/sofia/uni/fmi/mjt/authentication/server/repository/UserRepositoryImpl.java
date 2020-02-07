package bg.sofia.uni.fmi.mjt.authentication.server.repository;

import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;
import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.server.utils.PasswordEncryptor;
import bg.sofia.uni.fmi.mjt.authentication.server.utils.adapters.LocalDateTimeInterfaceAdapter;
import bg.sofia.uni.fmi.mjt.authentication.server.utils.adapters.PasswordEncryptorInterfaceAdapter;
import bg.sofia.uni.fmi.mjt.authentication.server.utils.adapters.UserInterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class UserRepositoryImpl implements UserRepository {
    public static final String COUNT_NOT_SUPPORT_MESSAGE = "Method count of UserRepository is not supported.";
    public static final String USER_NULL_EXCEPTION_MESSAGE = "User is null";
    public static final String USERNAME_NULL_EXCEPTION_MESSAGE = "Username is null";

    public static final String USER_DIRECTORY = "users";

    private static final String DATA_FILE_EXTENSION = ".dat";
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(User.class, new UserInterfaceAdapter())
            .registerTypeAdapter(PasswordEncryptor.class, new PasswordEncryptorInterfaceAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeInterfaceAdapter())
            .create();

    public UserRepositoryImpl() throws IOException {
        if (!Files.exists(Path.of(USER_DIRECTORY))) {
            Files.createDirectories(Path.of(USER_DIRECTORY));
        }
    }

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
        throw new UnsupportedOperationException(COUNT_NOT_SUPPORT_MESSAGE);
    }

    @Override
    public User findOne(String username) {
        if (username == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        if (!exists(username)) {
            return null;
        }
        Path filePath = getFilePath(username);
        try (Reader reader = new FileReader(filePath.toString())) {
            Type userType = new TypeToken<User>() {
            }.getType();
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
        try (Stream<Path> stream = Files.walk(Paths.get(USER_DIRECTORY))) {
            return stream.filter(file -> !Files.isDirectory(file))
                    .map(path -> {
                        try (Reader reader = new FileReader(path.toString())){
                            return reader;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            return null;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(reader -> reader != null)
                    .map(reader -> {
                        try {
                            return GSON.fromJson(reader, User.class);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(user -> user != null)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return null;
        }
    }

    private static String formatFileName(String username) {
        return username + DATA_FILE_EXTENSION;
    }

    private static Path getFilePath(String username) {
        String fileName = formatFileName(username);
        return Path.of(USER_DIRECTORY, fileName);
    }
}
