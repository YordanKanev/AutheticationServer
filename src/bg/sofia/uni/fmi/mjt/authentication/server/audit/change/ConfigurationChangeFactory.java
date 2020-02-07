package bg.sofia.uni.fmi.mjt.authentication.server.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer.Issuer;
import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;

import java.util.UUID;

public interface ConfigurationChangeFactory {

    static StartedConfigurationChange startConfigurationChange(Change change, Issuer issuer) {
        if (issuer == null) {
            ;
            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        if (change == null) {
            ;
            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        UUID operationId = UUID.randomUUID();
        return new StartedConfigurationChangeImpl(change, operationId, issuer);
    }

    static FinishedConfigurationChange finishConfigurationChange(StartedConfigurationChange startedConfigurationChange,
                                                                 ChangeResult changeResult) {
        if (startedConfigurationChange == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        return new FinishedConfigurationChangeImpl(changeResult
                , startedConfigurationChange.getOperationId()
                , startedConfigurationChange.getIssuer());
    }
}
