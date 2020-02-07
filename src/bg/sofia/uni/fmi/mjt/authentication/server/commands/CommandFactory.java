package bg.sofia.uni.fmi.mjt.authentication.server.commands;

import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.AuthenticationEngine;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.CommandExecutor;
import bg.sofia.uni.fmi.mjt.authentication.server.locker.LoginLocker;
import org.apache.commons.cli.ParseException;

public interface CommandFactory {
    //Commands
    String REGISTER = "register";
    String LOGIN = "login";
    String UPDATE_USER = "update-user";
    String RESET_PASSWORD = "reset-password";
    String LOGOUT = "logout";
    String ADD_ADMIN_USER = "add-admin-user";
    String REMOVED_ADMIN_USER = "remove-admin-user";
    String DELETE_USER = "delete-user";


    interface CommandParameters {
        String USERNAME = "username";
        String PASSWORD = "password";
        String OLD_PASSWORD = "old-password";
        String NEW_PASSWORD = "new-password";
        String FIRST_NAME = "first-name";
        String LAST_NAME = "last-name";
        String EMAIL = "email";
        String SESSION_ID = "session-id";
        String NEW_EMAIL = "new-email";
        String NEW_USERNAME = "new-username";
        String NEW_FIRST_NAME = "new-first-name";
        String NEW_LAST_NAME = "new-last-name";
    }

    String COMMAND_NOT_FOUND_MESSAGE = "Command not found";
    String INVALID_ARGUMENTS = "Invalid arguments";

    static Command getInstance(Request request,
                               CommandExecutor commandExecutor,
                               AuthenticationEngine authenticationEngine,
                               LoginLocker loginLocker) throws ParseException {
        if (request == null || commandExecutor == null || authenticationEngine == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        Command result = null;
        String[] words = request.getRequestBody().split("\\s+");
        if (words.length < 2) {
            throw new IllegalArgumentException(INVALID_ARGUMENTS);
        }

        switch (words[0]) {
            case REGISTER:
                result = new RegisterCommand(request,
                        commandExecutor,
                        commandExecutor);
                break;
            case LOGIN:
                result = new LoginCommand(request,
                        commandExecutor,
                        authenticationEngine.getAuditLog(),
                        loginLocker);
                break;
            case UPDATE_USER:
                result = new UpdateUserCommand(request,
                        authenticationEngine.getUserRepository(),
                        authenticationEngine.getSessionStore());
                break;
            case RESET_PASSWORD:
                result = new ResetPasswordCommand(request,
                        authenticationEngine.getUserRepository(),
                        authenticationEngine.getSessionStore());
                break;
            case LOGOUT:
                result = new LogoutCommand(request,
                        authenticationEngine.getSessionStore());
                break;
            case ADD_ADMIN_USER:
                result = new CreateAdminCommand(request,
                        commandExecutor,
                        authenticationEngine.getAuditLog());
                break;
            case REMOVED_ADMIN_USER:
                result = new RemoveAdminCommand(request,
                        commandExecutor,
                        authenticationEngine.getAuditLog());
                break;
            case DELETE_USER:
                result = new DeleteUserCommand(request,
                        commandExecutor);
                break;
            default:
                throw new IllegalArgumentException(COMMAND_NOT_FOUND_MESSAGE);
        }
        return result;
    }
}
