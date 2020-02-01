package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.model.web.request.AdminOperation;

public interface AdminCreator {
    void createAdmin(AdminOperation user);
}
