package bg.sofia.uni.fmi.mjt.authentication.client;

public class ConsoleClient {

    public static void main(String[] args){
        final String serverHost = "localhost";
        final int serverPort = 8080;
        new Thread(new WebClient(System.in,System.out,serverHost,serverPort)).start();
    }
}
