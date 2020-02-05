package bg.sofia.uni.fmi.mjt.authentication.server.audit.change;

public interface ChangeResultFactory {
    static ChangeResult getInstance(boolean successful){
        return new ChangeResultImpl(successful);
    }
}
