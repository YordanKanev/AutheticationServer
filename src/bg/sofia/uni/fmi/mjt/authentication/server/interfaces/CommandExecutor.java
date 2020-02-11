package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;
import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.AdminOperation;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.PasswordChange;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.UserRegistration;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.UserUpdate;
import bg.sofia.uni.fmi.mjt.authentication.server.implementation.AdminOperator;
import bg.sofia.uni.fmi.mjt.authentication.server.implementation.LoginFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.implementation.LogoutFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.implementation.RegistratorFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.session.Session;

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
        if (authenticationEngine == null) {
            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        Login login = LoginFactory.getInstance(authenticationEngine.getUserRepository(),
                authenticationEngine.getSessionStore());
        Registrator registrator = RegistratorFactory.getInstance(authenticationEngine.getUserRepository());
        Logout logout = LogoutFactory.getInstance(authenticationEngine.getSessionStore());
        AdminCreator adminCreator = AdminOperator.getAdminCreator(authenticationEngine.getUserRepository(),
                authenticationEngine.getSessionStore());
        AdminRemover adminRemover = AdminOperator.getAdminRemover(authenticationEngine.getUserRepository(),
                authenticationEngine.getSessionStore());
        UserDeleter userDeleter = AdminOperator.getUserDeleter(authenticationEngine.getUserRepository(),
                authenticationEngine.getSessionStore());
        return new CommandExecutor() {
            @Override
            public User createAdmin(AdminOperation adminOperation) {
                return adminCreator.createAdmin(adminOperation);
            }

            @Override
            public User removeAdmin(AdminOperation adminOperation) {
                return adminRemover.removeAdmin(adminOperation);
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
                return logout.logout(sessionId);
            }

            @Override
            public void resetPassword(PasswordChange passwordChange) {

            }

            @Override
            public User register(UserRegistration user) {
                return registrator.register(user);
            }

            @Override
            public User deleteUser(AdminOperation adminOperation) {
                return userDeleter.deleteUser(adminOperation);
            }

            @Override
            public void updateUser(UserUpdate updatedUser) {

            }
        };
    }
}
