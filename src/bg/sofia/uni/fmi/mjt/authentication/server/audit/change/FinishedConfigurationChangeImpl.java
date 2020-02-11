package bg.sofia.uni.fmi.mjt.authentication.server.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer.Issuer;
import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;

import java.util.UUID;

public class FinishedConfigurationChangeImpl extends ConfigurationChangeImpl implements FinishedConfigurationChange {

    private ChangeResult changeResult;

    public FinishedConfigurationChangeImpl(ChangeResult changeResult, UUID operationId, Issuer issuer) {
        super(operationId, issuer);
        setChangeResult(changeResult);
    }

    @Override
    public ChangeResult getChangeResult() {
        return changeResult;
    }

    private void setChangeResult(ChangeResult changeResult) {
        if (changeResult == null) {
            ;
            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        this.changeResult = changeResult;
    }
}
