package bg.sofia.uni.fmi.mjt.authentication.commands;

import bg.sofia.uni.fmi.mjt.authentication.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.model.web.response.Response;
import bg.sofia.uni.fmi.mjt.authentication.model.web.response.ResponseFactory;
import bg.sofia.uni.fmi.mjt.authentication.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.session.SessionStore;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.UUID;

public class ResetPasswordCommand extends BasicCommand implements Secured {

    public static final String PASSWORD_NOT_UPDATED_MESSAGE = "Password was not updated successfully";
    public static final String PASWORD_UPDATED_MESSAGE = "Password updated successfully";

    private static final Option optionSessionId = CommandOptions.requiredOptionSessionId;
    private static final Option optionUsername = CommandOptions.requiredOptionUsername;
    private static final Option optionOldPasword = CommandOptions.requiredOptionOldPassword;
    private static final Option optionNewPassword = CommandOptions.requiredOptionNewPassword;
    private static final Options options = new Options().addOption(optionSessionId)
            .addOption(optionUsername)
            .addOption(optionOldPasword)
            .addOption(optionNewPassword);

    private String sessionId;
    private String username;
    private String oldPassword;
    private String newPassword;

    private UserRepository userRepository;
    private SessionStore sessionStore;

    public ResetPasswordCommand(Request request, UserRepository userRepository, SessionStore sessionStore) throws ParseException {
        super(request);
        if(userRepository == null || sessionStore == null) {
            throw new IllegalArgumentException();
        }
        String[] words = request.getRequestBody().split("\\s+");
        if (!words[0].equals(CommandFactory.RESET_PASSWORD)) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        CommandLine commandLine = parser.parse(options, words);
        this.sessionId = commandLine.getOptionValue(optionSessionId.getLongOpt());
        this.username = commandLine.getOptionValue(optionUsername.getLongOpt());
        this.oldPassword = commandLine.getOptionValue(optionOldPasword.getLongOpt());
        this.newPassword = commandLine.getOptionValue(optionNewPassword.getLongOpt());

        this.sessionStore = sessionStore;
        this.userRepository = userRepository;
    }
    @Override
    public Response execute() {
        try{
            if(!sessionStore.hasActiveSession(sessionId)){
                return ResponseFactory.error(Secured.INVALID_SESSION_ID_MESSAGE);
            }
            User user = userRepository.findOne(username);
            if(user == null){
                return ResponseFactory.error(UserRepository.USER_NOT_FOUND_MESSAGE);
            }
            if(!user.verifyPassword(oldPassword)){
                return ResponseFactory.error(User.PASSWORD_MISMATCH_MESSAGE);
            }
            user.setPassword(newPassword);
            User updated = userRepository.save(user);
            if(updated == null){
               return ResponseFactory.error(PASSWORD_NOT_UPDATED_MESSAGE);
            }
            return ResponseFactory.success(PASWORD_UPDATED_MESSAGE);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseFactory.error(e.getMessage());
        }
    }

    @Override
    public UUID getSessionId() {
        return UUID.fromString(sessionId);
    }
}
