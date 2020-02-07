package bg.sofia.uni.fmi.mjt.authentication.server.locker;

import bg.sofia.uni.fmi.mjt.authentication.server.AuthenticationServerConfiguration;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class LoginLockerImpl implements LoginLocker {

    private ConcurrentHashMap<String, LocalDateTime> locked = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer> attempts = new ConcurrentHashMap<>();
    private AuthenticationServerConfiguration authenticationServerConfiguration;
    public LoginLockerImpl(AuthenticationServerConfiguration authenticationServerConfiguration) {
        if(authenticationServerConfiguration == null){
            throw new IllegalArgumentException();
        }
        this.authenticationServerConfiguration = authenticationServerConfiguration;
    }

    @Override
    public boolean isLocked(String ipAddress){
        if(ipAddress == null){
            throw new IllegalArgumentException();
        }
        LocalDateTime lockedTo = locked.get(ipAddress);
        if(lockedTo == null){
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        if(lockedTo.isAfter(now)){
            return true;
        }
        locked.remove(ipAddress);
        return false;
    }

    @Override
    public void incrementAttempt(String ipAddress){
        if(ipAddress == null){
            throw new IllegalArgumentException();
        }
        Integer attemptsCount = attempts.merge(ipAddress,1,Integer::sum);
        if(attemptsCount >= authenticationServerConfiguration.getLoginAttemptsCount()){
            LocalDateTime lockedTo = LocalDateTime.now().plusSeconds(authenticationServerConfiguration.getLockTime());
            locked.put(ipAddress,lockedTo);
        }
    }
}
