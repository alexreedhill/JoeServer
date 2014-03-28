import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

public class Worker implements Runnable {
    private Socket clientSocket;
    private OutputStream clientOutputStream;
    private BufferedReader in;
    private String publicPath;
    private int bodyContentLength = 0;
    private char[] requestBodyBuffer = new char[0];

    public Worker(Socket clientSocket, String publicPath) {
        this.clientSocket = clientSocket;
        this.publicPath = publicPath;
    }

    public void run()  {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        System.out.println("Thread count: " + threadSet.size());
        if(threadSet.size() < 20) {
            try {
                Thread.sleep(100);
            } catch(InterruptedException ex) {
                System.err.println(ex);
            }
        }
        try {
            clientOutputStream = clientSocket.getOutputStream();
            InputStream clientInputStream = clientSocket.getInputStream();
            in = new BufferedReader(new InputStreamReader(clientInputStream));
            String httpRequest = parseHttpRequest();
            if (validRequest(httpRequest) ) {
                Request request = new RequestBuilder(httpRequest, publicPath).build();
                serveResponse(createResponse(request));
            }
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            try {
                clientSocket.close();
            } catch(IOException ex) {
                System.out.println("Client socket could not be closed");
            }
        }
    }

    private String parseHttpRequest() throws IOException {
        String httpRequest = "";
        String httpRequestLine = in.readLine();
        if(validRequest(httpRequestLine)) {
            httpRequest = httpRequestLine + "\r\n";
            while(!requestHeaderComplete(httpRequestLine)) {
                httpRequestLine = in.readLine();
                httpRequest += httpRequestLine + "\n";
                if(httpRequestLine.contains("Content-Length")) {
                    bodyContentLength = Integer.parseInt(httpRequestLine.split(": ")[1]);
                    requestBodyBuffer = new char[bodyContentLength];
                }
            }
            in.read(requestBodyBuffer, 0, bodyContentLength);
            httpRequest += new String(requestBodyBuffer);
        }
        return httpRequest;
    }

    private boolean validRequest(String httpRequest) throws IOException {
        ArrayList<String> invalidRequests = createInvalidRequests();
        return !invalidRequests.contains(httpRequest);
    }

    private ArrayList<String> createInvalidRequests() {
        return new ArrayList<String>() {{
            add("");
            add(null);
        }};
    }

    private byte[] createResponse(Request request) throws Exception {
        Dispatcher dispatcher = new Dispatcher();
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

    private boolean requestHeaderComplete(String httpRequestLine) throws IOException {
        return httpRequestLine == null || httpRequestLine.equals("") || httpRequestLine.contains("\r\n");
    }
}