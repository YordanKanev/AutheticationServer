package bg.sofia.uni.fmi.mjt.authentication.server.commands;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer.Issuer;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.issuer.IssuerFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;
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

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
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
        this.issuer = IssuerFactory.getInstance(getSessionId().toString(), request.getIPAddress());
    }

    @Override
    public UUID getSessionId() {
        return UUID.fromString(sessionId);
    }
}
