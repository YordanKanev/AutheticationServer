package bg.sofia.uni.fmi.mjt.authentication.server;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

import bg.sofia.uni.fmi.mjt.authentication.audit.AuditConfiguration;
import bg.sofia.uni.fmi.mjt.authentication.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.commands.Command;
import bg.sofia.uni.fmi.mjt.authentication.commands.CommandFactory;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.model.web.response.Response;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.AuthenticationEngine;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.CommandExecutor;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Registrator;
import bg.sofia.uni.fmi.mjt.authentication.session.SessionStore;
import bg.sofia.uni.fmi.mjt.authentication.repository.UserRepository;

public class AuthenticationServer implements AuthenticationController {




    private AuditLog auditLog;
    private SessionStore sessionStore;
    private WebServer webServer;
    private UserRepository userRepository;
    private AuthenticationServerConfiguration configuration;
    private AuditConfiguration auditConfiguration;
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
    private Registrator registrator;

    public AuthenticationServer(AuthenticationServerConfiguration configuration, AuditConfiguration auditConfiguration) throws IOException {
        if (configuration == null || auditConfiguration == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        this.configuration = configuration;
        this.auditConfiguration = auditConfiguration;
        WebServer webServer = WebServerBuilder.defaultWebServerBuilder().build(this);
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
            //TODO: set message
            throw new IllegalStateException();
        }
        this.webServer.close();
    }

    @Override
    public void onRequest(Request request, Consumer<Response> consumer) {
        if (request == null) {
            return;
        }
        Command command = CommandFactory.getInstance(request,
                CommandExecutor.defaultExecutor(authenticationEngine),
                authenticationEngine);
        Response response = command.execute();
        consumer.accept(response);
    }
}
