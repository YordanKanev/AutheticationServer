package bg.sofia.uni.fmi.mjt.authentication.server.audit.change;

public interface Change {
    String getUsername();
    boolean areRightsAdded();
}
