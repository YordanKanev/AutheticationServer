package bg.sofia.uni.fmi.mjt.authentication.model;

import java.util.UUID;
import java.util.concurrent.*;

public class SessionStoreImpl implements SessionStore {

    private ConcurrentHashMap<String, Session> usernameToSession = new ConcurrentHashMap<>();
    private ConcurrentHashMap<UUID, Session> sessionIdToSession = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private long ttl;

    /**
     *
     * @param ttl - Time in milliseconds to keep session active
     */
    public SessionStoreImpl(long ttl){
        this.ttl = ttl;
    }

    @Override
    public void createSession(String username){
        if(username == null){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        if(hasActiveSession(username)){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        Session session = SessionFactory.getInstance(username);
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

    @Override
    public boolean hasActiveSession(String username){
        if(username == null){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        return usernameToSession.contains(username);
    }

    @Override
    public boolean hasActiveSession(UUID sessionId){
        if(sessionId == null){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        return sessionIdToSession.contains(sessionId);
    }

    @Override
    public void deleteSession(String username){
        if(username == null) {
            //TODO: set message
            throw  new IllegalArgumentException();
        }
        Session session = usernameToSession.remove(username);
        if(session == null){
            return;
        }
        sessionIdToSession.remove(session.getSessionId());
    }

    @Override
    public void deleteSession(UUID sessionId) {
        if(sessionId == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        Session session = sessionIdToSession.remove(sessionId);
        if(session == null) {
            return;
        }

    }
}
