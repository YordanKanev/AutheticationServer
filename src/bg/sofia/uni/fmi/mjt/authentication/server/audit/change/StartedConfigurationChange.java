package bg.sofia.uni.fmi.mjt.authentication.server.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.Entry;

public interface StartedConfigurationChange extends ConfigurationChange, Entry {
    Change getChange();
}
