package bg.sofia.uni.fmi.mjt.authentication.model;

import java.util.UUID;

public interface SessionStore {

	boolean hasActiveSession(String username);
	boolean hasActiveSession(UUID sessionId);
	void createSession(String username);
	
}
