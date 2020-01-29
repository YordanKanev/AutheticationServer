package bg.sofia.uni.fmi.mjt.authentication.model;

import java.util.UUID;

public abstract class SecuredCommand implements Command, Secured {

    private UUID sessionId;

    @Override
    public UUID getSessionId(){
        return sessionId;
    }
}
