package bg.sofia.uni.fmi.mjt.authentication.client;

import bg.sofia.uni.fmi.mjt.authentication.server.common.ExceptionMessages;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class WebClient implements Runnable{
    public static final String DISCONNECTED = "disconnected";
    public static final String QUIT_COMMAND = "quit";
    private static final String MESSAGE_CONNECTED = "Connected to the server.";
    private static final int BUFFER_SIZE = 1024;
    private ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
    private Scanner scanner;
    private PrintStream printer;
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    private boolean isStopped = false;
    public WebClient(InputStream inputStream,
                     OutputStream outputStream,
                     String serverHost,
                     int serverPort){
        if(inputStream == null || outputStream == null || serverHost == null){
            throw new IllegalArgumentException(ExceptionMessages.ARGUMENT_CANNOT_BE_NULL);
        }
        this.scanner = new Scanner(inputStream);
        this.printer = new PrintStream(outputStream);
        this.SERVER_HOST = serverHost;
        this.SERVER_PORT = serverPort;
    }

    public void stop(){
        isStopped = true;
    }

    @Override
    public void run() {
        try(SocketChannel socketChannel = SocketChannel.open()){
            socketChannel.connect(new InetSocketAddress(SERVER_HOST,SERVER_PORT));
            socketChannel.finishConnect();
            while(!isStopped){
                String message = scanner.nextLine();

                if(QUIT_COMMAND.equals(message)){
                    break;
                }

                buffer.clear();
                buffer.put(message.getBytes());
                buffer.flip();
                socketChannel.write(buffer);

                buffer.clear();
                int read = socketChannel.read(buffer);
                if(read > 0) {
                    buffer.flip();
                    String reply = new String(buffer.array(), 0, buffer.limit());

                    printer.println(reply);
                }else if(read == -1){
                    printer.println(DISCONNECTED);
                    return;
                }

            }
        }catch (IOException e){
            printer.println(e.getMessage());
        }
    }
}
