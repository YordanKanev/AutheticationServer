package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

public interface CommandExecutor extends Registrator,
        Login,
        PasswordResetter,
        Logout,
        UserUpdater,
        AdminCreator,
        AdminRemover,
        UserDeleter,
        IssuerProvider {
}
