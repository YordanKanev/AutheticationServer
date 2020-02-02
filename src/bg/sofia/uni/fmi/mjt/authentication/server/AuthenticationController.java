package bg.sofia.uni.fmi.mjt.authentication.server;

import bg.sofia.uni.fmi.mjt.authentication.model.web.request.Request;

import java.util.function.Consumer;

public interface AuthenticationController {
	void onRequest(Request request, Consumer<String> consumer);
}
