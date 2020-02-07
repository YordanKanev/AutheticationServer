package bg.sofia.uni.fmi.mjt.authentication.server.commands;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.Issuer;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.AdminOperation;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.Request;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.UUID;

public abstract class AdminCommand extends BasicCommand implements Secured {

    private static final Option optionSessionId = CommandOptions.requiredOptionSessionId;
    private static final Option optionUsername = CommandOptions.requiredOptionUsername;
    private static final Options options = new Options().addOption(optionSessionId)
            .addOption(optionUsername);

    protected AdminOperation adminOperation;
    protected Issuer issuer;

    private String sessionId;
    private String username;
    protected AdminCommand(Request request, String command) throws ParseException {
        super(request);
        String[] words = request.getRequestBody().split("\\s+");
        if (!words[0].equals(command)) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        CommandLine commandLine = parser.parse(options, words);
        this.sessionId = commandLine.getOptionValue(optionSessionId.getLongOpt());
        this.username = commandLine.getOptionValue(optionUsername.getLongOpt());
        this.adminOperation = new AdminOperation() {
            @Override
            public UUID getSessionId() {
                return UUID.fromString(sessionId);
            }

            @Override
            public String getUsername() {
                return username;
            }
        };
        this.issuer = new Issuer() {
            @Override
            public String getIdentifier() {
                return getSessionId().toString();
            }

            @Override
            public String getIPAddress() {
                return request.getIPAddress();
            }
        };
    }

    @Override
    public UUID getSessionId() {
        return UUID.fromString(sessionId);
    }
}
