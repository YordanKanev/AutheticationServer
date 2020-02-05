package bg.sofia.uni.fmi.mjt.authentication.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class SessionStoreImpl implements SessionStore {

    private ConcurrentHashMap<String, Session> usernameToSession = new ConcurrentHashMap<>();
    private ConcurrentHashMap<UUID, Session> sessionIdToSession = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private long ttl;
    private Map<UUID, ScheduledFuture> expirations = new ConcurrentHashMap<>();

    /**
     * @param ttl - Time in milliseconds to keep session active
     */
    public SessionStoreImpl(long ttl) {
        this.ttl = ttl;
    }

    @Override
    public UUID createSession(String username) {
        if (username == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        if (hasActiveSession(username)) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        Session session = SessionFactory.getInstance(username);
        usernameToSession.put(username, session);
        sessionIdToSession.put(session.getSessionId(), session);
        setExpiration(session);
        return session.getSessionId();
    }

    private void setExpiration(Session session) {
        ScheduledFuture scheduledFuture = executorService.schedule(new Runnable() {
            @Override
            public void run() {
                usernameToSession.remove(session.getUsername());
                sessionIdToSession.remove(session.getSessionId());
            }
        }, ttl, TimeUnit.MILLISECONDS);
        expirations.put(session.getSessionId(), scheduledFuture);
    }

    private UUID refreshSession(Session session) {
        if (session == null) {
            return null;
        }
        ScheduledFuture scheduledFuture = expirations.remove(session.getSessionId());
        if (scheduledFuture == null) {
            return null;
        }
        boolean canceled = scheduledFuture.cancel(false);
        if (!canceled) {
            return null;
        }
        setExpiration(session);
        return session.getSessionId();
    }

    @Override
    public UUID refreshSession(String username) {
        if (username == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        Session session = usernameToSession.get(username);
        return refreshSession(session);
    }

    @Override
    public UUID refreshSession(UUID sessionId) {
        if (sessionId == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        Session session = sessionIdToSession.get(sessionId);
        return refreshSession(session);
    }

    @Override
    public boolean hasActiveSession(String username) {
        if (username == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        return usernameToSession.contains(username);
    }

    @Override
    public boolean hasActiveSession(UUID sessionId) {
        if (sessionId == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        return sessionIdToSession.contains(sessionId);
    }

    @Override
    public Session deleteSession(String username) {
        if (username == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        Session session = usernameToSession.remove(username);
        if (session == null) {
            return null;
        }
        return sessionIdToSession.remove(session.getSessionId());
    }

    @Override
    public Session deleteSession(UUID sessionId) {
        if (sessionId == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        Session session = sessionIdToSession.remove(sessionId);
        if (session == null) {
            return null;
        }
        return usernameToSession.remove(session.getUsername());
    }

    @Override
    public Session getSession(UUID sessionId) {
        if(sessionId == null){
            throw new IllegalArgumentException();
        }
        return sessionIdToSession.get(sessionId);
    }
}
