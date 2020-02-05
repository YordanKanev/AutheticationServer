package bg.sofia.uni.fmi.mjt.authentication.audit;

public interface Issuer {
    String getIdentifier();
    String getIPAddress();
}
