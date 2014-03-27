import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private String publicPath;
    protected boolean isStopped = false;
    private ArrayList invalidRequests = createInvalidRequests();

    public Server(int port, String publicPath) throws IOException {
        serverSocket = new ServerSocket(port);
        this.publicPath = publicPath;
    }

    public void run() {
        try {
            while(!isStopped) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new Worker(clientSocket, invalidRequests, publicPath)).start();
            }
        } catch(Exception ex) {
            System.err.println(ex);
        }
    }

    public synchronized void stop(){
        isStopped = true;
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private ArrayList createInvalidRequests() {
        return new ArrayList<String>() {{
            add("");
            add(null);
        }};
    }
}
