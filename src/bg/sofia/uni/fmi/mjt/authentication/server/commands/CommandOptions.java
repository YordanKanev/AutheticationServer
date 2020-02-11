package bg.sofia.uni.fmi.mjt.authentication.server.commands;

import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;
import org.apache.commons.cli.Option;

public interface CommandOptions {

    static Option.Builder getOptionBuilderFor(String optionName) {
        if (optionName == null) {
            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        return Option.builder()
                .hasArg(true)
                .longOpt(optionName);
    }

    Option.Builder usernameBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.USERNAME);
    Option.Builder passwordBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.PASSWORD);
    Option.Builder oldPasswordBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.OLD_PASSWORD);
    Option.Builder newPasswordBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.NEW_PASSWORD);
    Option.Builder firstNameBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.FIRST_NAME);
    Option.Builder lastNameBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.LAST_NAME);
    Option.Builder emailBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.EMAIL);
    Option.Builder sessionIdBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.SESSION_ID);
    Option.Builder newEmailBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.NEW_EMAIL);
    Option.Builder newUsernameBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.NEW_USERNAME);
    Option.Builder newFirstNameBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.NEW_FIRST_NAME);
    Option.Builder newLastNameBuilder = getOptionBuilderFor(CommandFactory.CommandParameters.NEW_LAST_NAME);

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
    Option optionNewEmail = newEmailBuilder
            .build();
    Option optionNewUsername = newUsernameBuilder
            .build();
    Option optionNewFirstName = newFirstNameBuilder
            .build();
    Option optionNewLastName = newLastNameBuilder
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
    Option requiredOptionNewEmail = newEmailBuilder
            .required()
            .build();
    Option requiredOptionNewUsername = newUsernameBuilder
            .required()
            .build();
    Option requiredOptionNewFirstName = newFirstNameBuilder
            .required()
            .build();
    Option requiredOptionNewLastName = newLastNameBuilder
            .required()
            .build();
}
