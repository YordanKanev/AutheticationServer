package bg.sofia.uni.fmi.mjt.authentication.server.audit;

public interface Issuer {
    String getIdentifier();
    String getIPAddress();
}
