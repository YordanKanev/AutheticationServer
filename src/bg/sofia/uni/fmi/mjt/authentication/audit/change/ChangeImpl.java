package bg.sofia.uni.fmi.mjt.authentication.audit.change;

public class ChangeImpl implements Change {

    private String username;
    private boolean rightsAdded;

    public ChangeImpl(String username, boolean rightsAdded){
        setUsername(username);
        setRightsAdded(rightsAdded);
    }
    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean areRightsAdded() {
        return false;
    }

    private void setUsername(String username){
        if(username == null){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        this.username = username;
    }

    private void setRightsAdded(boolean rightsAdded){
        this.rightsAdded = rightsAdded;
    }
}
