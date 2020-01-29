package bg.sofia.uni.fmi.mjt.authentication.audit;

import bg.sofia.uni.fmi.mjt.authentication.audit.change.ConfigurationChangeFactory;

public interface EntryFactory extends ConfigurationChangeFactory {

    static Entry failedLogin(Issuer issuer){
        if(issuer == null){
            //TODO: set message;
            throw new IllegalArgumentException();
        }
        return new FailedLoginEntry(issuer);
    }
}
