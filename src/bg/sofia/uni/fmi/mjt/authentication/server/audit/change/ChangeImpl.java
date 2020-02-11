package bg.sofia.uni.fmi.mjt.authentication.server.audit.change;

import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;

public class ChangeImpl implements Change {

    private String username;
    private boolean rightsAdded;

    public ChangeImpl(String username, boolean rightsAdded) {
        setUsername(username);
        setRightsAdded(rightsAdded);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean areRightsAdded() {
        return rightsAdded;
    }

    private void setUsername(String username) {
        if (username == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        this.username = username;
    }

    private void setRightsAdded(boolean rightsAdded) {
        this.rightsAdded = rightsAdded;
    }
}
