package bg.sofia.uni.fmi.mjt.authentication.commands;

import bg.sofia.uni.fmi.mjt.authentication.model.web.response.Response;

public interface Command {
    Response execute();
}
