package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.session.SessionStore;

public interface AuthenticationEngine {
    UserRepository getUserRepository();
    AuditLog getAuditLog();
    SessionStore getSessionStore();
}
