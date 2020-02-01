package bg.sofia.uni.fmi.mjt.authentication.server;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

import bg.sofia.uni.fmi.mjt.authentication.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.commands.Command;
import bg.sofia.uni.fmi.mjt.authentication.commands.CommandFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Registrator;
import bg.sofia.uni.fmi.mjt.authentication.session.SessionStore;
import bg.sofia.uni.fmi.mjt.authentication.repository.UserRepository;

public class AuthenticationServer implements AuthenticationController {

	public static final long DEFAULT_LOCK_TIME = 30000;
	public static final int DEFAULT_LOGIN_ATTEMPTS_COUNT = 3;
	public static final Path DEFAULT_AUDIT_DIRECTORY_PATH = Path.of("");
	
	private AuditLog auditLog;
	private SessionStore sessionStore;
	private WebServer webServer;
	private UserRepository userRepository;
	private AuthenticationServerConfiguration configuration;
	private Registrator registrator;
	
	public AuthenticationServer(AuthenticationServerConfiguration configuration) throws IOException {
		if(configuration == null) {
			//TODO: set message
			throw new IllegalArgumentException();
		}
		this.configuration = configuration;
		WebServer webServer = WebServerBuilder.defaultWebServerBuilder().build(this);
	}
	
	public AuthenticationServer defaultAuthenticationServer() throws IOException {
		return new AuthenticationServer(new AuthenticationServerConfiguration() {
			
			@Override
			public int getLoginAttemptsCount() {
				return DEFAULT_LOGIN_ATTEMPTS_COUNT;
			}
			
			@Override
			public long getLockTime() {
				return DEFAULT_LOCK_TIME;
			}
			
			@Override
			public Path getAuditDirectoryPath() {
				return DEFAULT_AUDIT_DIRECTORY_PATH;
			}
		});
	}
	
	public void start() throws Exception {
		this.webServer.start();
	}
	
	public void stop() throws Exception {
		if(webServer == null) {
			//TODO: set message
			throw new IllegalStateException();
		}
		this.webServer.close();
	}

	@Override
	public void onRequest(String request, Consumer<String> consumer) {
		if(request == null) {
			return;
		}
		Command command = CommandFactory.getInstance(request);
		command.execute();
	}
}
