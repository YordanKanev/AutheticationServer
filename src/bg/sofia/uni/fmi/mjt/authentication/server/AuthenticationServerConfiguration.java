package bg.sofia.uni.fmi.mjt.authentication.server;

import java.nio.file.Path;

public interface AuthenticationServerConfiguration {
	
	long getLockTime();
	int getLoginAttemptsCount();
	Path getAuditDirectoryPath();
}
