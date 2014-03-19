import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.util.ArrayList;


public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream clientOutputStream;
    private BufferedReader in;
    private String rawRequest;
    private ArrayList invalidRequests;
    private Dispatcher dispatcher = new Dispatcher();

    public Server() throws IOException{
        serverSocket = new ServerSocket(5000);
        invalidRequests = createInvalidRequestsList();
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
        Response response = dispatcher.dispatch(request);
        return response.respond();
    }

    private void serveResponse(byte[] fullResponse) throws IOException {
        DataOutputStream writer = new DataOutputStream(clientOutputStream);
        writer.write(fullResponse, 0, fullResponse.length);
        writer.flush();
        writer.close();
    }

    private boolean validRequest() throws IOException {
        return !invalidRequests.contains(rawRequest = in.readLine());
    }

    private ArrayList createInvalidRequestsList() {
        return new ArrayList<String>() {{
            add("Host: localhost:5000");
            add("");
            add(null);
        }};
    }
}
