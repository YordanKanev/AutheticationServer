package bg.sofia.uni.fmi.mjt.authentication.server.commands;

import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;
import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.Response;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.ResponseFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.authentication.server.session.Session;
import bg.sofia.uni.fmi.mjt.authentication.server.session.SessionStore;
import org.apache.commons.cli.*;

import java.util.UUID;

public class UpdateUserCommand extends BasicCommand implements Secured {

    public static final String USER_UPDATED_MESSAGE = "User updated.";
    public static final String USER_NOT_UPDATED_MESSAGE = "User not updated";

    private static Option optionSessionId = CommandOptions.requiredOptionSessionId;
    private static Option optionNewUsername = CommandOptions.optionNewUsername;
    private static Option optionNewFirstName = CommandOptions.optionNewFirstName;
    private static Option optionNewLastName = CommandOptions.optionNewLastName;
    private static Option optionNewEmail = CommandOptions.optionNewEmail;
    private static Options options = new Options().addOption(optionSessionId)
            .addOption(optionNewUsername)
            .addOption(optionNewFirstName)
            .addOption(optionNewLastName)
            .addOption(optionNewEmail);

    private String sessionId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    private UserRepository userRepository;
    private SessionStore sessionStore;
    public UpdateUserCommand(Request request, UserRepository userRepository, SessionStore sessionStore) throws ParseException {
        super(request);
        if(userRepository == null || sessionStore == null) {
            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        String[] words = request.getRequestBody().split("\\s+");
        if (!words[0].equals(CommandFactory.UPDATE_USER)) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        CommandLine commandLine = parser.parse(options, words);
        this.sessionId = commandLine.getOptionValue(optionSessionId.getLongOpt());
        this.username = commandLine.getOptionValue(optionNewUsername.getLongOpt());
        this.firstName = commandLine.getOptionValue(optionNewFirstName.getLongOpt());
        this.lastName = commandLine.getOptionValue(optionNewLastName.getLongOpt());
        this.email = commandLine.getOptionValue(optionNewEmail.getLongOpt());

        this.userRepository = userRepository;
        this.sessionStore = sessionStore;
    }

    @Override
    public Response execute() {
        try{
            if(!sessionStore.hasActiveSession(UUID.fromString(sessionId))){
                return ResponseFactory.error(Secured.INVALID_SESSION_ID_MESSAGE);
            }
            Session session = sessionStore.getSession(getSessionId());
            if(session == null){
                return ResponseFactory.error(Secured.INVALID_SESSION_ID_MESSAGE);
            }
            User user = userRepository.findOne(session.getUsername());
            if(user == null) {
                return ResponseFactory.error(UserRepository.USER_NOT_FOUND_MESSAGE);
            }
            if(username != null){
                user.setUsername(username);
            }
            if(firstName != null) {
                user.setFirstName(firstName);
            }
            if(lastName != null){
                user.setLastName(lastName);
            }
            if(email != null) {
                user.setEmail(email);
            }
            User updateUser = userRepository.save(user);
            if(updateUser == null) {
                return ResponseFactory.error(USER_NOT_UPDATED_MESSAGE);
            }
            return ResponseFactory.success(USER_UPDATED_MESSAGE);
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
