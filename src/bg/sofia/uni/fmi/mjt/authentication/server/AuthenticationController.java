package bg.sofia.uni.fmi.mjt.authentication.server;

import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.Response;

import java.util.function.Consumer;

public interface AuthenticationController {
    void onRequest(Request request, Consumer<Response> consumer);
}
