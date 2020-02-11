package bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer;

public interface IssuerFactory {
    static Issuer getInstance(String identifier, String ipAddress) {
        return new BasicIssuer(identifier, ipAddress);
    }
}
