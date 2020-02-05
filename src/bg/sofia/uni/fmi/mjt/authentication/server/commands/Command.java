package bg.sofia.uni.fmi.mjt.authentication.server.commands;

import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.Response;

public interface Command {
    Response execute();
}
