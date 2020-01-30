package bg.sofia.uni.fmi.mjt.authentication.server;

import java.util.function.Consumer;

public interface AuthenticationController {
	void onRequest(String request, Consumer<String> consumer);
}
