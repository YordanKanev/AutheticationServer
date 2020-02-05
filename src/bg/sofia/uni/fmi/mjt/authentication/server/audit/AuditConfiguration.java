package bg.sofia.uni.fmi.mjt.authentication.server.audit;

import java.nio.file.Path;

public interface AuditConfiguration {

    Path DEFAULT_AUDIT_DIRECTORY_PATH = Path.of("");

    Path getAuditDirectoryPath();

    static AuditConfiguration defaultConfiguration(){
        return new AuditConfiguration() {
            @Override
            public Path getAuditDirectoryPath() {
                return DEFAULT_AUDIT_DIRECTORY_PATH;
            }
        };
    }
}
