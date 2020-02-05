package bg.sofia.uni.fmi.mjt.authentication.server.repository;

import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;

public interface UserRepository {
    String USER_NOT_FOUND_MESSAGE = "User not found.";

    User delete(User user);
    boolean exists(String username);
    long count();
    User findOne(String username);
    <T extends User> T save(T user);
    Iterable<User> findAll();
}
