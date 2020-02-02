package bg.sofia.uni.fmi.mjt.authentication.model.web.request;

import java.net.SocketAddress;

public interface Request {
    String getRequestBody();
    String getIPAddress();
}
