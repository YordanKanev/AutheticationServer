package bg.sofia.uni.fmi.mjt.authentication.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.audit.Issuer;

import java.util.UUID;

public interface ConfigurationChangeFactory {

    static StartedConfigurationChange startConfigurationChange(Change change, Issuer issuer) {
        if (issuer == null) {
            //TODO: set message;
            throw new IllegalArgumentException();
        }
        if (change == null) {
            //TODO: set message;
            throw new IllegalArgumentException();
        }
        UUID operationId = UUID.randomUUID();
        return new StartedConfigurationChangeImpl(change, operationId, issuer);
    }

    static FinishedConfigurationChange finishConfigurationChange(StartedConfigurationChange startedConfigurationChange,
                                                                 ChangeResult changeResult) {
        if (startedConfigurationChange == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        return new FinishedConfigurationChangeImpl(changeResult
                , startedConfigurationChange.getOperationId()
                , startedConfigurationChange.getIssuer());
    }
}
