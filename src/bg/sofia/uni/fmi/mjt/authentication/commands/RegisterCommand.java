package bg.sofia.uni.fmi.mjt.authentication.commands;

import java.util.List;
import java.util.UUID;

import bg.sofia.uni.fmi.mjt.authentication.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.model.user.UserFactory;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.UserRegistration;
import bg.sofia.uni.fmi.mjt.authentication.model.web.response.Response;
import bg.sofia.uni.fmi.mjt.authentication.model.web.response.ResponseFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Login;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.Registrator;
import org.apache.commons.cli.*;

public class RegisterCommand implements Command {

    public static final String NOT_REGISTERED_MESSAGE = "Registration failed.";
    public static final String NOT_LOGGED_IN_MESSAGE = "Login failed.";

    private static final Option optionUsername = CommandOptions.requiredOptionUsername;
    private static final Option optionPassword = CommandOptions.requiredOptionPassword;
    private static final Option optionFirstName = CommandOptions.requiredOptionFirstName;
    private static final Option optionLastName = CommandOptions.requiredOptionLastName;
    private static final Option optionEmail = CommandOptions.requiredOptionEmail;
    private static final Options options = new Options().addOption(optionUsername)
            .addOption(optionPassword)
            .addOption(optionFirstName)
            .addOption(optionLastName)
            .addOption(optionEmail);
    private static final CommandLineParser parser = new DefaultParser();

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    private Registrator registrator;
    private Login login;

    public RegisterCommand(String request, Registrator registrator, Login login) throws ParseException {
        if (request == null || registrator == null || login == null) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        String[] words = request.split("\\s+");
        if (!words[0].equals(CommandFactory.REGISTER)) {
            //TODO: set message
            throw new IllegalArgumentException();
        }
        CommandLine commandLine = parser.parse(options, words);
        username = commandLine.getOptionValue(optionUsername.getLongOpt());
        password = commandLine.getOptionValue(optionPassword.getLongOpt());
        firstName = commandLine.getOptionValue(optionFirstName.getLongOpt());
        lastName = commandLine.getOptionValue(optionLastName.getLongOpt());
        email = commandLine.getOptionValue(optionEmail.getLongOpt());
        this.registrator = registrator;
        this.login = login;
    }

    @Override
    public Response execute() {
        try{
            User user = registrator.register(new UserRegistration() {
                @Override
                public String getUsername() {
                    return username;
                }

                @Override
                public String getPassword() {
                    return password;
                }

                @Override
                public String getFirstName() {
                    return firstName;
                }

                @Override
                public String getLastName() {
                    return lastName;
                }

                @Override
                public String getEmail() {
                    return email;
                }
            });
            if(user == null) {
                ResponseFactory.error(NOT_REGISTERED_MESSAGE);
            }
            UUID sessionId = login.login(username,password);
            if(sessionId == null){
                return ResponseFactory.error(NOT_LOGGED_IN_MESSAGE);
            }
            return ResponseFactory.success(sessionId.toString());
        }catch (Exception e){
            return ResponseFactory.error(e.getMessage());
        }

    }
}
