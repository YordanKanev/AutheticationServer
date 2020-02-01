package bg.sofia.uni.fmi.mjt.authentication.session;

import java.util.UUID;

public interface SessionStore {
	boolean hasActiveSession(String username);
	boolean hasActiveSession(UUID sessionId);
	void createSession(String username);
	void deleteSession(String username);
	void deleteSession(UUID sessionId);
}
