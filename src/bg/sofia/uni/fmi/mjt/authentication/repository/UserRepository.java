package bg.sofia.uni.fmi.mjt.authentication.repository;

import bg.sofia.uni.fmi.mjt.authentication.model.user.User;

public interface UserRepository {
    User delete(User user);
    boolean exists(String username);
    long count();
    User findOne(String username);
    <T extends User> T save(T user);
    Iterable<User> findAll();
}
