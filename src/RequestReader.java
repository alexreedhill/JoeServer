import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestReader {
    private String httpRequest = "";
    private int bodyContentLength = 0;
    private char[] requestBodyBuffer = new char[0];
    private BufferedReader in;
    private InputStream clientInputStream;

    public RequestReader(Socket clientSocket) throws IOException {
        clientInputStream = clientSocket.getInputStream();
    }

    public String readRequest() throws IOException {
        in = new BufferedReader(new InputStreamReader(clientInputStream));
        String httpRequestLine = in.readLine();
        RequestValidator validator = new RequestValidator();
        if(validator.validate(httpRequestLine)) {
            httpRequest = httpRequestLine + "\r\n";
            readUntilEndOfHeaders(httpRequestLine);
            readBody();
        }
        return httpRequest;
    }

    private void readUntilEndOfHeaders(String httpRequestLine) throws IOException {
        while(!requestHeaderComplete(httpRequestLine)) {
            httpRequestLine = in.readLine();
            httpRequest += httpRequestLine + "\n";
            if(httpRequestLine.contains("Content-Length")) {
                bodyContentLength = Integer.parseInt(httpRequestLine.split(": ")[1]);
                requestBodyBuffer = new char[bodyContentLength];
            }
        }
    }

    private boolean requestHeaderComplete(String httpRequestLine) throws IOException {
        return httpRequestLine == null || httpRequestLine.equals("") || httpRequestLine.contains("\r\n");
    }

    private void readBody() throws IOException {
        in.read(requestBodyBuffer, 0, bodyContentLength);
        httpRequest += new String(requestBodyBuffer);
    }
}
