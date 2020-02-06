package bg.sofia.uni.fmi.mjt.authentication.server;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.AuditConfiguration;

import java.io.IOException;
import java.util.Scanner;

public class ServerRunner {

    public static void main(String[] args) throws Exception {
        AuthenticationServer authenticationServer =
                new AuthenticationServer(AuthenticationServerConfiguration.defaultConfiguration(),
                        AuditConfiguration.defaultConfiguration());
        authenticationServer.start();
        new Scanner(System.in).nextLine();
        authenticationServer.stop();
    }
}
