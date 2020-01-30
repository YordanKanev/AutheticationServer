package bg.sofia.uni.fmi.mjt.authentication.server;

import java.io.IOException;

public interface WebServerBuilder {
	String DEFAULT_HOST = "localhost";
	int DEFAULT_PORT = 8080;
	
	WebServerBuilder setServerHost(String serverHost);
	WebServerBuilder setServerPort(int serverPort);
	String getServerHost();
	int getServerPort();
	default WebServer build(AuthenticationController authenticationController) throws IOException {
		if(authenticationController == null) {
			//TODO: set message;
			throw new IllegalArgumentException();
		}
		return new WebServerImpl(getServerHost(),getServerPort(), authenticationController);
	}
	static WebServerBuilder defaultWebServerBuilder() {
		return new WebServerBuilder() {
			
			private String serverHost = DEFAULT_HOST;
			private int serverPort = DEFAULT_PORT;
			
			@Override
			public WebServerBuilder setServerPort(int serverPort) {
				this.serverPort = serverPort;
				return this;
			}
			
			@Override
			public WebServerBuilder setServerHost(String serverHost) {
				if(serverHost == null) {
					//TODO: set message
					throw new IllegalArgumentException();
				}
				this.serverHost = serverHost;
				return this;
			}
			
			@Override
			public int getServerPort() {
				return serverPort;
			}
			
			@Override
			public String getServerHost() {
				return serverHost;
			}
		};
	}
}
