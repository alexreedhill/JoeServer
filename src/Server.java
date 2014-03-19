import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.DataOutputStream;


public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream clientOutputStream;
    private BufferedReader in;
    String rawRequest;

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
            setupStreams();
            if (validRequest() ) {
                serveResponse(createResponse());
            }
            clientSocket.close();
        }
    }

    private void setupStreams() throws IOException {
        clientSocket = serverSocket.accept();
        clientOutputStream = clientSocket.getOutputStream();
        InputStream clientInputStream = clientSocket.getInputStream();
        in = new BufferedReader(new InputStreamReader(clientInputStream));
    }

    private byte[] createResponse() throws Exception {
        Request request = new Request(rawRequest);
        Response response = new Response(request);
        return response.respond();
    }

    private void serveResponse(byte[] fullResponse) throws IOException {
        DataOutputStream writer = new DataOutputStream(clientOutputStream);
        writer.write(fullResponse, 0, fullResponse.length);
        writer.flush();
        writer.close();
    }

    private boolean validRequest() throws IOException {
        return (rawRequest = in.readLine()) != "Host: localhost:5000" && rawRequest != null;
    }
}
