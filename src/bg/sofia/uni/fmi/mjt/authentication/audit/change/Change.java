package bg.sofia.uni.fmi.mjt.authentication.audit.change;

public interface Change {
    String getUsername();
    boolean areRightsAdded();
}
