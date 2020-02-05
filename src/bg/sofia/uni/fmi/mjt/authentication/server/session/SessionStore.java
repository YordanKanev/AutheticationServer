package bg.sofia.uni.fmi.mjt.authentication.server.session;

import java.util.UUID;

public interface SessionStore {
	boolean hasActiveSession(String username);
	boolean hasActiveSession(UUID sessionId);
	UUID createSession(String username);
	UUID refreshSession(String username);
	UUID refreshSession(UUID sessionId);
	Session deleteSession(String username);
	Session deleteSession(UUID sessionId);
	Session getSession(UUID sessionId);
}
