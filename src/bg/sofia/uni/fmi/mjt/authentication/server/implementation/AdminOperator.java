package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.AdminOperation;
import bg.sofia.uni.fmi.mjt.authentication.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.AdminCreator;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.AdminRemover;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.UserDeleter;
import bg.sofia.uni.fmi.mjt.authentication.session.Session;
import bg.sofia.uni.fmi.mjt.authentication.session.SessionStore;

public class AdminOperator implements AdminRemover, AdminCreator, UserDeleter {

    private UserRepository userRepository;
    private SessionStore sessionStore;

    private AdminOperator(UserRepository userRepository, SessionStore sessionStore){
        if(userRepository == null || sessionStore == null){
            throw new IllegalArgumentException();
        }
        this.userRepository = userRepository;
        this.sessionStore = sessionStore;
    }
    private boolean hasPermission(AdminOperation adminOperation){
        if(adminOperation == null){
            return false;
        }
        if(!sessionStore.hasActiveSession(adminOperation.getSessionId())){
            return false;
        }
        Session session = sessionStore.getSession(adminOperation.getSessionId());
        if(session == null){
            return false;
        }
        User user = userRepository.findOne(session.getUsername());
        if(user == null){
            return false;
        }
        if(!user.isAdmin()){
            return  false;
        }
        return true;
    }

    private User changeAdminRights(AdminOperation adminOperation, boolean isAdmin){
        if(!hasPermission(adminOperation)){
            return null;
        }
        User user = userRepository.findOne(adminOperation.getUsername());
        if(user == null){
            return null;
        }
        user.setAdmin(isAdmin);
        return userRepository.save(user);
    }
    @Override
    public User createAdmin(AdminOperation adminOperation) {
        return changeAdminRights(adminOperation, true);
    }

    @Override
    public User removeAdmin(AdminOperation adminOperation) {
        return changeAdminRights(adminOperation, false);
    }

    @Override
    public User deleteUser(AdminOperation adminOperation) {
        if(!hasPermission(adminOperation)){
            return null;
        }
        User user = userRepository.findOne(adminOperation.getUsername());
        if(user == null){
            return null;
        }
        return userRepository.delete(user);
    }

    public static AdminCreator getAdminCreator(UserRepository userRepository,SessionStore sessionStore){
        return new AdminOperator(userRepository,sessionStore);
    }

    public static AdminRemover getAdminRemover(UserRepository userRepository, SessionStore sessionStore) {
        return new AdminOperator(userRepository,sessionStore);
    }

    public static UserDeleter getUserDeleter(UserRepository userRepository, SessionStore sessionStore) {
        return new AdminOperator(userRepository,sessionStore);
    }
}
