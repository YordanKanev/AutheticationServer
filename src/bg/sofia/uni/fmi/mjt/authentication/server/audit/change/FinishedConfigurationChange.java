package bg.sofia.uni.fmi.mjt.authentication.server.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.Entry;

public interface FinishedConfigurationChange extends ConfigurationChange, Entry {
    ChangeResult getChangeResult();
}
