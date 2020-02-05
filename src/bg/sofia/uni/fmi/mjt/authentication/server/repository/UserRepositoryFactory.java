package bg.sofia.uni.fmi.mjt.authentication.server.repository;

public interface UserRepositoryFactory {

    static UserRepository getInstance(){
        return new UserRepositoryImpl();
    }
}
