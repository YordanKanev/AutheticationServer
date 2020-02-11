package bg.sofia.uni.fmi.mjt.authentication.server;

import java.io.IOException;
import java.util.function.Consumer;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.AuditConfiguration;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.change.AuditLogFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.commands.Command;
import bg.sofia.uni.fmi.mjt.authentication.server.commands.CommandFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.Response;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.ResponseFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.repository.UserRepositoryFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.AuthenticationEngine;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.CommandExecutor;
import bg.sofia.uni.fmi.mjt.authentication.server.locker.LoginLocker;
import bg.sofia.uni.fmi.mjt.authentication.server.locker.LoginLockerFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.session.SessionStore;
import bg.sofia.uni.fmi.mjt.authentication.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.session.SessionStoreFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.webserver.WebServer;
import bg.sofia.uni.fmi.mjt.authentication.server.webserver.WebServerBuilder;

public class AuthenticationServer implements AuthenticationController {


    private AuditLog auditLog;
    private SessionStore sessionStore;
    private WebServer webServer;
    private UserRepository userRepository;
    private AuthenticationServerConfiguration configuration;
    private AuditConfiguration auditConfiguration;
    private LoginLocker loginLocker;
    private CommandExecutor commandExecutor;
    private AuthenticationEngine authenticationEngine = new AuthenticationEngine() {
        @Override
        public UserRepository getUserRepository() {
            return userRepository;
        }

        @Override
        public AuditLog getAuditLog() {
            return auditLog;
        }

        @Override
        public SessionStore getSessionStore() {
            return sessionStore;
        }

        @Override
        public AuthenticationServerConfiguration getConfiguration() {
            return configuration;
        }
    };

    public AuthenticationServer(AuthenticationServerConfiguration configuration, AuditConfiguration auditConfiguration) throws IOException {
        if (configuration == null || auditConfiguration == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        this.configuration = configuration;
        this.auditConfiguration = auditConfiguration;
        this.auditLog = AuditLogFactory.getInstance(auditConfiguration);
        this.userRepository = UserRepositoryFactory.getInstance();
        this.sessionStore = SessionStoreFactory.getInstance();
        this.webServer = WebServerBuilder.defaultWebServerBuilder().build(this);
        this.commandExecutor = CommandExecutor.defaultExecutor(authenticationEngine);
        this.loginLocker = LoginLockerFactory.getInstance(configuration);
    }

    public static AuthenticationServer defaultAuthenticationServer() throws IOException {
        return new AuthenticationServer(AuthenticationServerConfiguration.defaultConfiguration(),
                AuditConfiguration.defaultConfiguration());
    }

    public void start() throws Exception {
        this.webServer.start();
    }

    public void stop() throws Exception {
        if (webServer == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        this.webServer.close();
    }

    @Override
    public void onRequest(Request request, Consumer<Response> consumer) {
        if (request == null) {
            return;
        }
        try {
            Command command = CommandFactory.getInstance(request,
                    commandExecutor,
                    authenticationEngine,
                    loginLocker);
            Response response = command.execute();
            consumer.accept(response);
        } catch (Exception e) {
            consumer.accept(ResponseFactory.error(e.getMessage()));
        }
    }
}
