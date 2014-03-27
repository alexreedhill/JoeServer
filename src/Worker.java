import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Worker implements Runnable {
    private Socket clientSocket;
    private OutputStream clientOutputStream;
    private BufferedReader in;
    private Dispatcher dispatcher = new Dispatcher();
    private ArrayList invalidRequests = createInvalidRequests();

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            clientOutputStream = clientSocket.getOutputStream();
            InputStream clientInputStream = clientSocket.getInputStream();
            long time = System.currentTimeMillis();
            in = new BufferedReader(new InputStreamReader(clientInputStream));
            String fullRawRequest = parseFullRawRequest();
            if (validRequest(fullRawRequest) ) {
                Request request = new RequestBuilder().build(fullRawRequest);
                serveResponse(createResponse(request));
            }
            clientSocket.close();
            System.out.println("Request processed: " + time);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private String parseFullRawRequest() throws IOException {
        String fullRawRequest = "";
        String rawRequestLine = in.readLine();
        if(validRequest(rawRequestLine)) {
            fullRawRequest = rawRequestLine + "\r\n";
            while(!requestHeaderComplete(rawRequestLine)) {
                rawRequestLine = in.readLine();
                fullRawRequest += rawRequestLine + "\n";
            }
        }
        return fullRawRequest;
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

    private ArrayList createInvalidRequests() {
        return new ArrayList<String>() {{
            add("");
            add(null);
        }};
    }
}

