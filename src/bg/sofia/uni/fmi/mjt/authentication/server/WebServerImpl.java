package bg.sofia.uni.fmi.mjt.authentication.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class WebServerImpl implements WebServer {

    private static final int BUFFER_SIZE = 1024;
    private static final long SLEEP_MILLIS = 100L;
    private final String serverHost;
    private final int serverPort;
    private AuthenticationController authenticationController;
    private ServerSocketChannel ssc;
    private Selector selector;
    private ByteBuffer requestByteBuffer;
    private ByteBuffer responseByteBuffer;
    private boolean started = false;
    private Stoppable requestHandler = new Stoppable() {
		
    	private boolean stopped = false;
    	
    	@Override
    	public void stop() {
    		stopped = true;
    	}
    	
		@Override
		public void run() {
			try {
				while(true) {
					if(stopped) {
						ssc.close();
						selector.close();
						requestByteBuffer.clear();
						responseByteBuffer.clear();
						return;
					}
					int readyChannels = selector.select();
	                if (readyChannels == 0) {
	                    try {
	                        Thread.sleep(SLEEP_MILLIS);
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                    continue;
	                }
	                
	                Set<SelectionKey> selectedKeys = selector.selectedKeys();
	                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

	                while (keyIterator.hasNext()) {
	                    SelectionKey key = keyIterator.next();
	                    if (key.isReadable()) {
	                        SocketChannel sc = (SocketChannel) key.channel();

	                        requestByteBuffer.clear();
	                        int r = sc.read(requestByteBuffer);
	                        if (r <= 0) {
	                            sc.close();
	                        } else {
	                        	String request = new String(requestByteBuffer.array(), 0, requestByteBuffer.limit());
	                        	String ipAddress = sc.getRemoteAddress().toString();
	                        	authenticationController.onRequest(request, ipAddress, (response) -> {
	                        		responseByteBuffer.clear();
	                        		responseByteBuffer.put(response.getBytes());
	                        		responseByteBuffer.flip();
	                                try {
										sc.write(responseByteBuffer);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
	                        	});  
	                        }
	                    } else if (key.isAcceptable()) {
	                        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
	                        SocketChannel accept = sockChannel.accept();
	                        accept.configureBlocking(false);
	                        accept.register(selector, SelectionKey.OP_READ);
	                    }

	                    keyIterator.remove();
	                }
				}
			}catch(Exception e) {		
				throw new RuntimeException();
			}
			
		}
	};

    protected WebServerImpl(String serverHost, int serverPort, 
    		AuthenticationController authenticationController) throws IOException{
        if(serverHost == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.authenticationController = authenticationController;
    }
    
    
	@Override
	public void close() throws Exception {
		if(!started || ssc == null) {
			//TODO: set message
			throw new IllegalStateException();
		}
		requestHandler.stop();
	}


	@Override
	public void start() throws IOException {
		if(started) {
			//TODO: set message
			throw new IllegalStateException();
		}
		ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(serverHost, serverPort));
        ssc.configureBlocking(false);
        selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        requestByteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		new Thread(requestHandler).start();
		started = true;
	}


}
