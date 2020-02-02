package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.audit.Issuer;

public interface IssuerProvider {
    Issuer getIssuer();
}
