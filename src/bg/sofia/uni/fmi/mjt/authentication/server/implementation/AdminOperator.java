package bg.sofia.uni.fmi.mjt.authentication.server.implementation;

import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.AdminOperation;
import bg.sofia.uni.fmi.mjt.authentication.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.AdminCreator;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.AdminRemover;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.UserDeleter;
import bg.sofia.uni.fmi.mjt.authentication.server.session.Session;
import bg.sofia.uni.fmi.mjt.authentication.server.session.SessionStore;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        return checkSessionPermission(session);
    }

    private boolean checkSessionPermission(Session session) {
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

    private User removeSelfRights(AdminOperation adminOperation){
        Iterable<User> users = userRepository.findAll();
        if(users == null){
            return null;
        }
        Iterator<User> iterator = users.iterator();
        int count = 0;
        boolean match = false;
        User userToUpdate = null;
        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getUsername().equals(adminOperation.getUsername())){
                match = true;
                userToUpdate = user;
            }
            if(user.isAdmin()){
                count++;
            }
            if(count > 1 && match){
                break;
            }
            iterator.remove();
        }
        if(!match || count < 2){
            return null;
        }
        if(userToUpdate == null){
            return null;
        }
        userToUpdate.setAdmin(false);
        return userRepository.save(userToUpdate);
    }
    private User removeOtherRights(AdminOperation adminOperation){
        User user = userRepository.findOne(adminOperation.getUsername());
        if(user == null){
            return null;
        }
        user.setAdmin(false);
        return userRepository.save(user);
    }
    @Override
    public User removeAdmin(AdminOperation adminOperation) {
        if(adminOperation == null){
            throw new IllegalArgumentException();
        }
        Session session = sessionStore.getSession(adminOperation.getSessionId());
        if(!checkSessionPermission(session)){
            return null;
        }
        if(session.getUsername().equals(adminOperation.getUsername())){
            //self remove rights
            return removeSelfRights(adminOperation);
        }else{
            //remove rights of other
            return removeOtherRights(adminOperation);
        }
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
        User deleted = userRepository.delete(user);
        if(deleted == null){
            return null;
        }
        sessionStore.deleteSession(adminOperation.getUsername());
        return deleted;
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
