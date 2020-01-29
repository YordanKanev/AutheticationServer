package bg.sofia.uni.fmi.mjt.authentication.repository;

public interface UserRepositoryFactory {

    static UserRepository getInstance(){
        return new UserRepositoryImpl();
    }
}
