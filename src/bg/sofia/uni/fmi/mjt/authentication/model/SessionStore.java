package bg.sofia.uni.fmi.mjt.authentication.model;

import java.util.UUID;
import java.util.concurrent.*;

public class SessionStore {

    private ConcurrentHashMap<String, SessionImpl> usernameToSession = new ConcurrentHashMap<>();
    private ConcurrentHashMap<UUID, SessionImpl> sessionIdToSession = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private long ttl;

    /**
     *
     * @param ttl - Time in milliseconds to keep session active
     */
    public SessionStore(long ttl){
        this.ttl = ttl;
    }

    public void createSession(String username){
        if(username == null){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        if(hasActiveSession(username)){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        SessionImpl session = SessionFactory.getInstance(username);
        usernameToSession.put(username,session);
        sessionIdToSession.put(session.getSessionId(),session);
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                usernameToSession.remove(username);
                sessionIdToSession.remove(session.getSessionId());
            }
        },ttl, TimeUnit.MILLISECONDS);
    }

    public boolean hasActiveSession(String username){
        if(username == null){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        return usernameToSession.contains(username);
    }

    public boolean hasActiveSession(UUID sessionId){
        if(sessionId == null){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        return sessionIdToSession.contains(sessionId);
    }

    public void deleteSession(String username){
        if(username == null) {
            //TODO: set message
            throw  new IllegalArgumentException();
        }
        SessionImpl session = usernameToSession.remove(username);
        if(session == null){
            return;
        }
        sessionIdToSession.remove(session.getSessionId());
    }

    public void deleteSession(UUID sessionId) {
        if(sessionId == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        SessionImpl session = sessionIdToSession.remove(sessionId);
        if(session == null) {
            return;
        }

    }
}
