package bg.sofia.uni.fmi.mjt.authentication.server.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.BasicEntry;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.EntryType;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer.Issuer;

import java.util.UUID;

public class ConfigurationChangeImpl extends BasicEntry implements ConfigurationChange{

    private UUID operationId;

    protected ConfigurationChangeImpl(UUID operationId, Issuer issuer) {
        super(EntryType.CONFIGURATION_CHANGE, issuer);
        setOperationId(operationId);
    }

    @Override
    public UUID getOperationId() {
        return operationId;
    }

    private void setOperationId(UUID operationId){
        if(operationId == null){
            //TODO: set message;
            throw new IllegalArgumentException();
        }
        this.operationId = operationId;
    }
}
