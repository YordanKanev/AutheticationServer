package bg.sofia.uni.fmi.mjt.authentication.server.commands;

import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.Response;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.ResponseFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.session.Session;
import bg.sofia.uni.fmi.mjt.authentication.server.session.SessionStore;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.UUID;

public class LogoutCommand extends BasicCommand implements Secured {

    public static final String LOGOUT_SUCCESS_MESSAGE = "Logout successfully";
    public static final String LOGOUT_FAIL_MESSAGE = "Logout failed";

    private static final Option optionSessionId = CommandOptions.requiredOptionSessionId;
    private static final Options options = new Options().addOption(optionSessionId);

    private String sessionId;

    private SessionStore sessionStore;

    public LogoutCommand(Request request, SessionStore sessionStore) throws ParseException {
        super(request);
        if(sessionStore == null){
            throw new IllegalArgumentException();
        }
        String[] words = request.getRequestBody().split("\\s+");
        if (!words[0].equals(CommandFactory.LOGOUT)) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        CommandLine commandLine = parser.parse(options, words);
        this.sessionId = commandLine.getOptionValue(optionSessionId.getLongOpt());
        this.sessionStore = sessionStore;
    }

    @Override
    public Response execute() {
        try{
            Session session = sessionStore.deleteSession(sessionId);
            if(session != null){
                return ResponseFactory.success(LOGOUT_SUCCESS_MESSAGE);
            }else{
                return ResponseFactory.error(LOGOUT_FAIL_MESSAGE);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseFactory.error(e.getMessage());
        }
    }

    @Override
    public UUID getSessionId() {
        return UUID.fromString(sessionId);
    }
}
