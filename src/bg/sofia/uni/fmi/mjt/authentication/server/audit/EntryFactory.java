package bg.sofia.uni.fmi.mjt.authentication.server.audit;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.change.ConfigurationChangeFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer.Issuer;
import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;

public interface EntryFactory extends ConfigurationChangeFactory {

    static Entry failedLogin(Issuer issuer){
        if(issuer == null){
            ;
            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        return new FailedLoginEntry(issuer);
    }
}
