import java.io.*;
import java.net.*;

/**
 * Created by alexhill on 3/13/14.
 */
public class MockClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public MockClient() throws IOException {
        socket = new Socket("localhost", 5000);
    }

    public static void main(String[] args) {
        try {
            MockClient client = new MockClient();
            client.sendRequest("GET", "/");
        } catch(IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }

    public String requestResponseStatusCode(String method, String path) throws IOException {
        String response = sendRequest(method, path);
        return getResponseStatusCode(response);
    }

    private String sendRequest(String method, String path) throws IOException {
        InputStreamReader networkStreamReader = new InputStreamReader(socket.getInputStream());
        in = new BufferedReader(networkStreamReader);
        out = new PrintWriter(socket.getOutputStream());
        out.print(method + " " + path + " " + "HTTP/1.0\r\n");
        String line;
        StringBuffer response = new StringBuffer();
        while((line = in.readLine()) != null) {
            response.append(line);
            response.append("\r\n");
            System.out.println(line);
        }
        return response.toString();
    }

    private String getResponseStatusCode(String response) throws IOException {
        String firstResponseLine = getFirstResponseLine(response);
        String statusCode = firstResponseLine.split(" ")[1];
        return statusCode;
    }

    private String getFirstResponseLine(String response) {
        String firstResponseLine = response.split("\n")[0];
        return firstResponseLine;
    }
}
