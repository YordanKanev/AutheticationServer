package bg.sofia.uni.fmi.mjt.authentication.commands;

import bg.sofia.uni.fmi.mjt.authentication.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.model.web.response.Response;
import bg.sofia.uni.fmi.mjt.authentication.model.web.response.ResponseFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.UserDeleter;
import org.apache.commons.cli.ParseException;

public class DeleteUserCommand extends AdminCommand {

    public static final String USER_NOT_DELETED_MESSAGE = "User not deleted.";
    public static final String USER_DELETED_MESSAGE = "User deleted.";

    private UserDeleter userDeleter;

    public DeleteUserCommand(Request request, UserDeleter userDeleter) throws ParseException {
        super(request, CommandFactory.DELETE_USER);
        if(userDeleter == null){
            throw new IllegalArgumentException();
        }
        this.userDeleter = userDeleter;
    }
    @Override
    public Response execute() {
        try{
            User user = userDeleter.deleteUser(this.adminOperation);
            Response response = null;
            if(user == null){
                response = ResponseFactory.error(USER_NOT_DELETED_MESSAGE);
            }
            response = ResponseFactory.success(USER_DELETED_MESSAGE);
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return ResponseFactory.error(e.getMessage());
        }
    }
}
