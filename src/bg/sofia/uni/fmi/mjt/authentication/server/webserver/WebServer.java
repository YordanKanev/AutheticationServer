package bg.sofia.uni.fmi.mjt.authentication.server.webserver;

import java.io.IOException;

public interface WebServer extends AutoCloseable{
	void start() throws IOException;
}
