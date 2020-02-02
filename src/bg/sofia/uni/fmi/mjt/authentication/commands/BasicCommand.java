package bg.sofia.uni.fmi.mjt.authentication.commands;

import bg.sofia.uni.fmi.mjt.authentication.model.web.request.Request;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

public abstract class BasicCommand implements Command{

    protected Request request;
    protected static final CommandLineParser parser = new DefaultParser();

    protected BasicCommand(Request request){
        if(request == null){
            //TODO: set message
            throw new IllegalArgumentException();
        }
        this.request = request;
    }

}
