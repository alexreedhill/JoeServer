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
        } catch(Exception ex) {
            System.err.println(ex);
        }
    }

    public void run() throws Exception {
        while(true) {
            setupStreams();
            String fullRawRequest = parseFullRawRequest();
            if (validRequest(fullRawRequest) ) {
                Request request = new RequestBuilder().build(fullRawRequest);
                serveResponse(createResponse(request));
            }
            clientSocket.close();
        }
    }

    private String parseFullRawRequest() throws IOException {
        String rawRequestLine = in.readLine();
        String fullRawRequest = rawRequestLine + "\r\n";
        while(!requestHeaderComplete(rawRequestLine)) {
            rawRequestLine = in.readLine();
            fullRawRequest += rawRequestLine + "\n";
        }
        return fullRawRequest;
    }

    private void setupStreams() throws IOException {
        clientSocket = serverSocket.accept();
        clientOutputStream = clientSocket.getOutputStream();
        InputStream clientInputStream = clientSocket.getInputStream();
        in = new BufferedReader(new InputStreamReader(clientInputStream));
    }

    private byte[] createResponse(Request request) throws Exception {
        Response response = dispatcher.dispatch(request);
        System.out.println("Full response: " + new String(response.fullResponse, "UTF-8"));
        return response.fullResponse;
    }

    private void serveResponse(byte[] fullResponse) throws IOException {
        DataOutputStream writer = new DataOutputStream(clientOutputStream);
        writer.write(fullResponse, 0, fullResponse.length);
        writer.flush();
        writer.close();
    }

    private boolean validRequest(String fullRawRequest) throws IOException {
        return !invalidRequests.contains(fullRawRequest);
    }

    private boolean requestHeaderComplete(String rawRequestLine) throws IOException {
        return rawRequestLine == null || rawRequestLine.equals("") || rawRequestLine.contains("\r\n");
    }

    private ArrayList createInvalidRequestsList() {
        return new ArrayList<String>() {{
            add("");
            add(null);
        }};
    }
}
