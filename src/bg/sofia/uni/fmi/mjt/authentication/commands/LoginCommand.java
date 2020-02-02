package bg.sofia.uni.fmi.mjt.authentication.commands;

import bg.sofia.uni.fmi.mjt.authentication.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.audit.EntryFactory;
import bg.sofia.uni.fmi.mjt.authentication.audit.Issuer;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.model.web.response.Response;
import bg.sofia.uni.fmi.mjt.authentication.model.web.response.ResponseFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.IssuerProvider;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Login;
import org.apache.commons.cli.*;

import java.util.UUID;

public class LoginCommand extends BasicCommand {

    private static final String LOGIN_FAILED_MESSAGE = "Login failed.";

    private static final Option optionUsername = CommandOptions.optionUsername;
    private static final Option optionPassword = CommandOptions.optionPassword;
    private static final Option optionSessionId = CommandOptions.optionSessionId;
    private static final Options options = new Options().addOption(optionUsername)
            .addOption(optionPassword)
            .addOption(optionSessionId);
    private String username;
    private String password;
    private String sessionId;

    private Login login;
    private AuditLog auditLog;
    private Issuer issuer;

    public LoginCommand(Request request, Login login, AuditLog auditLog) throws ParseException {
        super(request);
        if(request == null || login == null || auditLog == null){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        String[] words = request.getRequestBody().split("\\s+");
        if (!words[0].equals(CommandFactory.LOGIN)) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        CommandLine commandLine = parser.parse(options, words);
        username = commandLine.getOptionValue(optionUsername.getLongOpt());
        password = commandLine.getOptionValue(optionPassword.getLongOpt());
        sessionId = commandLine.getOptionValue(optionSessionId.getLongOpt());

        this.login = login;
        this.auditLog = auditLog;
        this.issuer = issuer;
    }
    @Override
    public Response execute() {
        try{
            boolean tryLoginWithCredentials = username != null && password != null;
            boolean tryLoginWithSessionId = sessionId != null;
            UUID sessionId;
            if(tryLoginWithCredentials){
                sessionId = login.login(username,password);
            }else{
                sessionId = login.login(UUID.fromString(this.sessionId));
            }
            if(sessionId == null){
                auditLog.log(EntryFactory.failedLogin(issuer));
                return ResponseFactory.error(LOGIN_FAILED_MESSAGE);
            }
            return ResponseFactory.success(sessionId.toString());
        }catch (Exception e) {
            return ResponseFactory.error(e.getMessage());
        }
    }
}
