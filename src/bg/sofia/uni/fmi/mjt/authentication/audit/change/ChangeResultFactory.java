package bg.sofia.uni.fmi.mjt.authentication.audit.change;

public interface ChangeResultFactory {
    static ChangeResult getInstance(boolean successful){
        return new ChangeResultImpl(successful);
    }
}
