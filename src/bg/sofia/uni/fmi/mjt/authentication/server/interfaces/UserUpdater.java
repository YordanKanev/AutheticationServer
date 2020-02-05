package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.UserUpdate;

public interface UserUpdater {
    void updateUser(UserUpdate updatedUser);
}
