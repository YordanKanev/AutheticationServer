package bg.sofia.uni.fmi.mjt.authentication.server.audit;

import java.io.IOException;

public interface AuditLog {

	void log(Entry entry) throws IOException;
}
