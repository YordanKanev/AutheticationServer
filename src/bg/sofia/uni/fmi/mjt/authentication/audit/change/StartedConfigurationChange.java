package bg.sofia.uni.fmi.mjt.authentication.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.audit.Entry;
import bg.sofia.uni.fmi.mjt.authentication.audit.change.Change;
import bg.sofia.uni.fmi.mjt.authentication.audit.change.ConfigurationChange;

public interface StartedConfigurationChange extends ConfigurationChange, Entry {
    Change getChange();
}
