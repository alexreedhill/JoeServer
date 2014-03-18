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

    public static void main(String[] args) throws Exception {
        try  {
            Server server = new Server();
            server.run();
        } catch(IOException ex) {
            System.err.println(ex);
            System.exit(1);
        } catch(ArrayIndexOutOfBoundsException ex) {
            System.err.println(ex);
        }
    }

    public void run() throws Exception {
        while(true) {
            Socket clientSocket = serverSocket.accept();
            OutputStream clientOutputStream = clientSocket.getOutputStream();
            InputStream clientInputStream = clientSocket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientInputStream));
            String input;
            if((input = in.readLine()) != "Host: localhost:5000" && input != null) {
                Request request = new Request(input);
                Response response = new Response(request);
                DataOutputStream writer = new DataOutputStream(clientOutputStream);
                byte[] fullResponse = response.respond();
                writer.write(fullResponse, 0, fullResponse.length);
                System.out.println("Response: " + new String(fullResponse, "UTF-8"));
                writer.flush();
                writer.close();
            }
            clientSocket.close();
        }

    }
}
