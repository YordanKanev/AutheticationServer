package bg.sofia.uni.fmi.mjt.authentication.server.commands;

import org.apache.commons.cli.Option;

public interface CommandOptions {

    Option.Builder usernameBuilder = Option.builder()
            .hasArg(true)
            .longOpt(CommandFactory.CommandParameters.USERNAME);
    Option.Builder passwordBuilder = Option.builder()
            .hasArg(true)
            .longOpt(CommandFactory.CommandParameters.PASSWORD);
    Option.Builder oldPasswordBuilder = Option.builder()
            .hasArg(true)
            .longOpt(CommandFactory.CommandParameters.OLD_PASSWORD);
    Option.Builder newPasswordBuilder = Option.builder()
            .hasArg(true)
            .longOpt(CommandFactory.CommandParameters.NEW_PASSWORD);
    Option.Builder firstNameBuilder = Option.builder()
            .hasArg(true)
            .longOpt(CommandFactory.CommandParameters.FIRST_NAME);
    Option.Builder lastNameBuilder = Option.builder()
            .hasArg(true)
            .longOpt(CommandFactory.CommandParameters.LAST_NAME);
    Option.Builder emailBuilder = Option.builder()
            .hasArg(true)
            .longOpt(CommandFactory.CommandParameters.EMAIL);
    Option.Builder sessionIdBuilder = Option.builder()
            .hasArg(true)
            .longOpt(CommandFactory.CommandParameters.SESSION_ID);

    Option optionUsername = usernameBuilder
            .build();
    Option optionPassword = passwordBuilder
            .build();
    Option optionFirstName = firstNameBuilder
            .build();
    Option optionLastName = lastNameBuilder
            .build();
    Option optionEmail = emailBuilder
            .build();
    Option optionSessionId = sessionIdBuilder
            .build();

    Option requiredOptionUsername = usernameBuilder
            .required()
            .build();
    Option requiredOptionPassword = passwordBuilder
            .required()
            .build();
    Option requiredOptionOldPassword = oldPasswordBuilder
            .required()
            .build();
    Option requiredOptionNewPassword = newPasswordBuilder
            .required()
            .build();
    Option requiredOptionFirstName = firstNameBuilder
            .required()
            .build();
    Option requiredOptionLastName = lastNameBuilder
            .required()
            .build();
    Option requiredOptionEmail = emailBuilder
            .required()
            .build();
    Option requiredOptionSessionId = sessionIdBuilder
            .required()
            .build();
}
