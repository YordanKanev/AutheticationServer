package bg.sofia.uni.fmi.mjt.authentication.server.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.AuditConfiguration;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.AuditLogImpl;

public interface AuditLogFactory {
    static AuditLog getInstance(AuditConfiguration auditConfiguration) {
        return new AuditLogImpl(auditConfiguration);
    }
}
