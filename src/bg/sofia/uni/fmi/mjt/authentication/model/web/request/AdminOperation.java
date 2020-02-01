package bg.sofia.uni.fmi.mjt.authentication.model.web.request;

import java.util.UUID;

public interface AdminOperation {
    UUID getSessionId();
    String getUsername();
}
