package bg.sofia.uni.fmi.mjt.authentication.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.audit.BasicEntry;
import bg.sofia.uni.fmi.mjt.authentication.audit.EntryType;
import bg.sofia.uni.fmi.mjt.authentication.audit.Issuer;

import java.util.UUID;

public class ConfigurationChangeImpl extends BasicEntry implements ConfigurationChange{

    private UUID operationId;

    protected ConfigurationChangeImpl(UUID operationId, Issuer issuer) {
        super(EntryType.CONFIGURATION_CHANGE, issuer);
        setOperationId(operationId);
    }

    @Override
    public UUID getOperationId() {
        return null;
    }

    private void setOperationId(UUID operationId){
        if(operationId == null){
            //TODO: set message;
            throw new IllegalArgumentException();
        }
        this.operationId = operationId;
    }
}
