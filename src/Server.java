/**
 * Created by alexhill on 3/13/14.
 */
import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Server() throws IOException{
        serverSocket = new ServerSocket(5000);
    }

    public static void main(String[] args) {
        try  {
            Server server = new Server();
            server.run();
        } catch(IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }

    public void run() throws IOException {
        while(true) {
            clientSocket = serverSocket.accept();
            OutputStream clientOutputStream = clientSocket.getOutputStream();
            out = new PrintWriter(clientOutputStream, true);
            InputStream clientInputStream = clientSocket.getInputStream();
            in = new BufferedReader(new InputStreamReader(clientInputStream));
            Response response = new Response(in.readLine());
            out.println("HTTP/1.0 200 OK\r\n");
            out.println("Foo");
            clientSocket.close();
        }

    }
}
