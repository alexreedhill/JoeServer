import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Worker implements Runnable {
    private Socket clientSocket;
    private OutputStream clientOutputStream;
    private String publicPath;
    private Dispatcher dispatcher;

    public Worker(Socket clientSocket, String publicPath) {
        this.clientSocket = clientSocket;
        this.publicPath = publicPath;
        dispatcher = new Dispatcher();
    }

    public void run()  {
        try {
            clientOutputStream = clientSocket.getOutputStream();
            RequestValidator validator = new RequestValidator();
            String httpRequest = new RequestReader(clientSocket.getInputStream(), validator).read();
            if (validator.validate(httpRequest) ) {
                byte[] response = getResponseBytes(httpRequest);
                serveResponse(response);
            }
            clientSocket.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private byte[] getResponseBytes(String httpRequest) throws Exception {
        Request request = new RequestBuilder(httpRequest, publicPath).build();
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

}