import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private String publicPath;
    protected boolean isStopped = false;
    private ExecutorService executor;

    public Server(int port, String publicPath) throws IOException {
        serverSocket = new ServerSocket(port);
        this.publicPath = publicPath;
        this.executor = Executors.newFixedThreadPool(8);
    }

    public void run() {
        try {
            while(!isStopped) {
                listen();
            }
        } catch(Exception ex) {
            System.err.println(ex);
        }
    }

    private void listen() throws IOException {
        Socket clientSocket = serverSocket.accept();
        if (clientSocket != null) {
            executor.execute(new Worker(clientSocket, publicPath));
        }
    }

}
