package bg.sofia.uni.fmi.mjt.authentication.server.model.web.response;

class ResponseImpl implements Response {

    private String message;
    private boolean successful;

    public ResponseImpl(boolean successful, String message) {
        this.successful = successful;
        this.message = message != null ? message : "";
    }

    @Override
    public boolean isSuccessful() {
        return successful;
    }

    @Override
    public String getResponseMessage() {
        return message;
    }
}
