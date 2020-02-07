package bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer;

class BasicIssuer implements Issuer {

    private String identifier;
    private String ipAddress;
    public BasicIssuer(String identifier, String ipAddress){
        if(identifier == null || ipAddress == null){
            throw new IllegalArgumentException();
        }
        this.identifier = identifier;
        this.ipAddress = ipAddress;
    }
    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getIPAddress() {
        return ipAddress;
    }
}
