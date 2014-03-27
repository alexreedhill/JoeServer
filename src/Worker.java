import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Worker implements Runnable {
    private Socket clientSocket;
    private OutputStream clientOutputStream;
    private BufferedReader in;
    private Dispatcher dispatcher = new Dispatcher();
    private ArrayList invalidRequests;
    private String publicPath;
    private int bodyContentLength = 0;
    private char[] requestBodyBuffer = new char[0];

    public Worker(Socket clientSocket, ArrayList invalidRequests, String publicPath) {
        this.clientSocket = clientSocket;
        this.invalidRequests = invalidRequests;
        this.publicPath = publicPath;
    }

    public void run() {
        try {
            clientOutputStream = clientSocket.getOutputStream();
            InputStream clientInputStream = clientSocket.getInputStream();
            long time = System.currentTimeMillis();
            in = new BufferedReader(new InputStreamReader(clientInputStream));
            String fullRawRequest = parseFullRawRequest();
            if (validRequest(fullRawRequest) ) {
                Request request = new RequestBuilder(fullRawRequest, publicPath).build();
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
                if(rawRequestLine.contains("Content-Length")) {
                    bodyContentLength = Integer.parseInt(rawRequestLine.split(": ")[1]);
                    requestBodyBuffer = new char[bodyContentLength];
                }
            }
            in.read(requestBodyBuffer, 0, bodyContentLength);
            fullRawRequest += new String(requestBodyBuffer);
        }
        return fullRawRequest;
    }

    private boolean validRequest(String fullRawRequest) throws IOException {
        return !invalidRequests.contains(fullRawRequest);
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

    private boolean requestHeaderComplete(String rawRequestLine) throws IOException {
        return rawRequestLine == null || rawRequestLine.equals("") || rawRequestLine.contains("\r\n");
    }
}
