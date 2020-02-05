package bg.sofia.uni.fmi.mjt.authentication.server.model.web.request;

public interface UserRegistration {
    String getUsername();
    String getPassword();
    String getFirstName();
    String getLastName();
    String getEmail();
}
