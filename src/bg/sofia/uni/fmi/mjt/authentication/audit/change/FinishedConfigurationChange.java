package bg.sofia.uni.fmi.mjt.authentication.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.audit.Entry;
import bg.sofia.uni.fmi.mjt.authentication.audit.change.ChangeResult;
import bg.sofia.uni.fmi.mjt.authentication.audit.change.ConfigurationChange;

public interface FinishedConfigurationChange extends ConfigurationChange, Entry {
    ChangeResult getChangeResult();
}
