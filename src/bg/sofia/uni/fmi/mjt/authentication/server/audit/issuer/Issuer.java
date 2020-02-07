package bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer;

public interface Issuer {
    String getIdentifier();
    String getIPAddress();
}
