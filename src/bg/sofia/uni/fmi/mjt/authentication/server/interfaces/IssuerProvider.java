package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer.Issuer;

public interface IssuerProvider {
    Issuer getIssuer();
}
