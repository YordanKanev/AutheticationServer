package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.AuthenticationServerConfiguration;
import bg.sofia.uni.fmi.mjt.authentication.server.session.SessionStore;

public interface AuthenticationEngine {
    UserRepository getUserRepository();
    AuditLog getAuditLog();
    SessionStore getSessionStore();
    AuthenticationServerConfiguration getConfiguration();
}
