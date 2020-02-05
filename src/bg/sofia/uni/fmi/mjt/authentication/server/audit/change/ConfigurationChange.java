package bg.sofia.uni.fmi.mjt.authentication.server.audit.change;

import java.util.UUID;

public interface ConfigurationChange {
    UUID getOperationId();
}
