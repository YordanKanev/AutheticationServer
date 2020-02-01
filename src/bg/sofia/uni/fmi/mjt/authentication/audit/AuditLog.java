package bg.sofia.uni.fmi.mjt.authentication.audit;

import java.io.IOException;

public interface AuditLog {

	void log(Entry entry) throws IOException;
}
