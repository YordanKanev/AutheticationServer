package bg.sofia.uni.fmi.mjt.authentication.server.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer.Issuer;
import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;

import java.util.UUID;

public class StartedConfigurationChangeImpl extends ConfigurationChangeImpl implements StartedConfigurationChange {

    private Change change;

    public StartedConfigurationChangeImpl(Change change, UUID operationId, Issuer issuer) {
        super(operationId, issuer);
        setChange(change);
    }

    @Override
    public Change getChange() {
        return change;
    }

    private void setChange(Change change){
        if(change == null){
            ;
            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        this.change = change;
    }
}
