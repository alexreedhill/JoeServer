package mocks;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;



public class MockClient {
    private Socket socket;

    public MockClient() throws IOException {
        socket = new Socket("localhost", 5000);
    }

    public static void main(String[] args) {
        try {
            MockClient client = new MockClient();
            client.sendRequest(args[0], args[1]);
        } catch(IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    private String sendRequest(String method, String path) throws IOException {
        InputStreamReader networkStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader in = new BufferedReader(networkStreamReader);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.print(method + " " + path + " " + "HTTP/1.0\r\n");
        String line;
        StringBuilder response = new StringBuilder();
        while((line = in.readLine()) != null) {
            response.append(line);
            response.append("\r\n");
        }
        return response.toString();
    }
}
