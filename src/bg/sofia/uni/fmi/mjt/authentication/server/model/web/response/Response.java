package bg.sofia.uni.fmi.mjt.authentication.server.model.web.response;

public interface Response {
    boolean isSuccessful();
    String getResponseMessage();
}
