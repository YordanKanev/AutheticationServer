package bg.sofia.uni.fmi.mjt.authentication.server.locker;

public interface LoginLocker {
    String LOCKED_MESSAGE = "You are locked. Try again later.";

    boolean isLocked(String ipAddress);

    void incrementAttempt(String ipAddress);
}
