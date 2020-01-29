package bg.sofia.uni.fmi.mjt.authentication.audit;

public interface Issuer {
    String getUsername();
    String getIPAddress();
}
