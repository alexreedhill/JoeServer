import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    protected boolean isStopped = false;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        try {
            while(!isStopped) {
                clientSocket = serverSocket.accept();
                new Thread(new Worker(clientSocket)).start();
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
}
