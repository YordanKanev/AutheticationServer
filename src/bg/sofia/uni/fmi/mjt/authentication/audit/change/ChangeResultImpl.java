package bg.sofia.uni.fmi.mjt.authentication.audit.change;

public class ChangeResultImpl implements ChangeResult {

    private boolean successfull;

    public ChangeResultImpl(boolean successfull) {
        setSuccessfull(successfull);
    }

    @Override
    public boolean isSuccessful() {
        return successfull;
    }

    private void setSuccessfull(boolean successfull){
        this.successfull = successfull;
    }
}
