package bg.sofia.uni.fmi.mjt.authentication.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.audit.Issuer;

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

    private void setChangeResult(ChangeResult changeResult){
        if(changeResult == null){
            //TODO: set message;
            throw new IllegalArgumentException();
        }
        this.changeResult = changeResult;
    }
}
