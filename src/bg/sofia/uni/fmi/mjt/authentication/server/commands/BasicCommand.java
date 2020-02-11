package bg.sofia.uni.fmi.mjt.authentication.server.commands;

import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.Request;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

public abstract class BasicCommand implements Command {

    protected Request request;
    protected final CommandLineParser parser;

    protected BasicCommand(Request request) {
        if (request == null) {

            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        this.parser = new DefaultParser();
        this.request = request;
    }

}
