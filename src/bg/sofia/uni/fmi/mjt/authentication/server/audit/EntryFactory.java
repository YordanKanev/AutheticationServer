package bg.sofia.uni.fmi.mjt.authentication.server.audit;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.change.ConfigurationChangeFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer.Issuer;

public interface EntryFactory extends ConfigurationChangeFactory {

    static Entry failedLogin(Issuer issuer){
        if(issuer == null){
            //TODO: set message;
            throw new IllegalArgumentException();
        }
        return new FailedLoginEntry(issuer);
    }
}
