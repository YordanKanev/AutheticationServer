package bg.sofia.uni.fmi.mjt.authentication.server.interfaces;

import bg.sofia.uni.fmi.mjt.authentication.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.AdminOperation;

public interface AdminRemover {
    User removeAdmin(AdminOperation adminOperation);
}
