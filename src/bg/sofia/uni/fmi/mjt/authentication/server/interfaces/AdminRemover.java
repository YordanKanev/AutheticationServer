package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.AdminOperation;

public interface AdminRemover {
    User removeAdmin(AdminOperation adminOperation);
}
