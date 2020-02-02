package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.commands.Command;
import bg.sofia.uni.fmi.mjt.authentication.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.AdminOperation;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.PasswordChange;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.UserRegistration;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.UserUpdate;
import bg.sofia.uni.fmi.mjt.authentication.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.implementation.LoginFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.implementation.RegistratorFactory;
import bg.sofia.uni.fmi.mjt.authentication.session.Session;
import bg.sofia.uni.fmi.mjt.authentication.session.SessionStore;

import java.util.UUID;

public interface CommandExecutor extends Registrator,
        Login,
        PasswordResetter,
        Logout,
        UserUpdater,
        AdminCreator,
        AdminRemover,
        UserDeleter {

    static CommandExecutor defaultExecutor(AuthenticationEngine authenticationEngine) {
        if(authenticationEngine == null){
            throw new IllegalArgumentException();
        }
        Login login = LoginFactory.getInstance(authenticationEngine.getUserRepository(), authenticationEngine.getSessionStore());
        Registrator registrator = RegistratorFactory.getInstance(authenticationEngine.getUserRepository());
        return new CommandExecutor() {
            @Override
            public void createAdmin(AdminOperation user) {

            }

            @Override
            public void removeAdmin(AdminOperation adminOperation) {

            }

            @Override
            public UUID login(UUID sessionId) {
                return login.login(sessionId);
            }

            @Override
            public UUID login(String username, String password) {
                return login.login(username, password);
            }

            @Override
            public Session logout(UUID sessionId) {
                return null;
            }

            @Override
            public void resetPassword(PasswordChange passwordChange) {

            }

            @Override
            public User register(UserRegistration user) {
                return registrator.register(user);
            }

            @Override
            public void deleteUser(AdminOperation adminOperation) {

            }

            @Override
            public void updateUser(UserUpdate updatedUser) {

            }
        };
    }
}
