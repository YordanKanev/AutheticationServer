package bg.sofia.uni.fmi.mjt.authentication.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.audit.AuditConfiguration;
import bg.sofia.uni.fmi.mjt.authentication.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.audit.AuditLogImpl;

public interface AuditLogFactory {
    static AuditLog getInstance(AuditConfiguration auditConfiguration){
        return new AuditLogImpl(auditConfiguration);
    }
}
