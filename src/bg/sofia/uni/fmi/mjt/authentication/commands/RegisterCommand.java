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

    private static final Option optionUsername = Option.builder()
            .longOpt(CommandFactory.CommandParameters.USERNAME)
            .required()
            .build();
    private static final Option optionPassword = Option.builder()
            .longOpt(CommandFactory.CommandParameters.PASSWORD)
            .required()
            .build();
    private static final Option optionFirstName = Option.builder()
            .longOpt(CommandFactory.CommandParameters.FIRST_NAME)
            .required()
            .build();
    private static final Option optionLastName = Option.builder()
            .longOpt(CommandFactory.CommandParameters.LAST_NAME)
            .required()
            .build();
    private static final Option optionEmail = Option.builder()
            .longOpt(CommandFactory.CommandParameters.EMAIL)
            .required()
            .build();
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
            registrator.register(new UserRegistration() {
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
            UUID sessionId = login.login(username,password);
            return ResponseFactory.success(sessionId.toString());
        }catch (Exception e){
            return ResponseFactory.error(e.getMessage());
        }

    }
}
