package bg.sofia.uni.fmi.mjt.authentication.audit.change;

public interface ChangeFactory {
    static Change getInstance(String username, boolean rightsAdded) {
        return new ChangeImpl(username, rightsAdded);
    }
}
