/**
 * Created by alexhill on 3/13/14.
 */
import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;

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
            Socket clientSocket = serverSocket.accept();
            OutputStream clientOutputStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(clientOutputStream, true);
            InputStream clientInputStream = clientSocket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientInputStream));
            Request request = new Request(in.readLine());
            Response response = new Response(request);
            out.println(response.getFullResponse());
            clientSocket.close();
        }

    }
}
