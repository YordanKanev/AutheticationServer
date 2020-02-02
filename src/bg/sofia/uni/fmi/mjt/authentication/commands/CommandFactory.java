package bg.sofia.uni.fmi.mjt.authentication.commands;

import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.CommandExecutor;

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
        String FIRST_NAME = "first-name";
        String LAST_NAME = "last-name";
        String EMAIL = "email";
        String SESSION_ID = "session-id";
    }

    static Command getInstance(String request, CommandExecutor commandExecutor) {
        if (request == null || commandExecutor == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        Command result = null;
        String[] words = request.split("\\s+");
        if (words.length < 2) {
            //TODO: throw exception
        }

        try {
            switch (words[0]) {
                case REGISTER:
                    result = new RegisterCommand(request, commandExecutor, commandExecutor);
                    break;
                case LOGIN:
                    result = new LoginCommand(request, commandExecutor);
                    break;
                case UPDATE_USER:
                    break;
                case RESET_PASSWORD:
                    break;
                case LOGOUT:
                    break;
                case ADD_ADMIN_USER:
                    break;
                case REMOVED_ADMIN_USER:
                    break;
                case DELETE_USER:
                    break;
                default: //TODO: throw exception; remove break;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
