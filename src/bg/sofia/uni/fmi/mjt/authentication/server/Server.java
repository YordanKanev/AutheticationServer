package bg.sofia.uni.fmi.mjt.authentication.server;

import java.io.Closeable;
import java.io.IOException;

public class Server implements Closeable {

    private final String SERVER_HOST;
    private final int SERVER_PORT;

    protected Server(String serverHost, int serverPort){
        if(serverHost == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        SERVER_HOST = serverHost;
        SERVER_PORT = serverPort;
    }


    @Override
    public void close() throws IOException {

    }
}
