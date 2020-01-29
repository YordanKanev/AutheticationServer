package bg.sofia.uni.fmi.mjt.authentication.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.audit.Issuer;

import java.util.UUID;

public class StartedConfigurationChangeImpl extends BasicConfigurationChange implements StartedConfigurationChange {

    private Change change;

    public StartedConfigurationChangeImpl(Change change, UUID operationId, Issuer issuer) {
        super(operationId, issuer);
    }

    @Override
    public Change getChange() {
        return change;
    }

    private void setChange(Change change){
        if(change == null){
            //TODO: set message;
            throw new IllegalArgumentException();
        }
        this.change = change;
    }
}
