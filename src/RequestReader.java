import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestReader {
    private String httpRequest = "";
    private int bodyContentLength = 0;
    private char[] requestBodyBuffer = new char[0];
    private BufferedReader in;
    private InputStream clientInputStream;
    private RequestValidator validator;

    public RequestReader(InputStream clientInputStream, RequestValidator validator) throws IOException {
        this.clientInputStream = clientInputStream;
        this.validator = validator;
    }

    public String readRequest() throws IOException {
        in = new BufferedReader(new InputStreamReader(clientInputStream));
        String httpRequestLine = in.readLine();
        if(validator.validate(httpRequestLine)) {
            readValidRequest(httpRequestLine);
        }
        return httpRequest;
    }

    private void readValidRequest(String httpRequestLine) throws IOException {
        httpRequest = httpRequestLine + "\r\n";
        readUntilEndOfHeaders(httpRequestLine);
        readBody();
    }

    private void readUntilEndOfHeaders(String httpRequestLine) throws IOException {
        while(!requestHeaderComplete(httpRequestLine)) {
            httpRequestLine = in.readLine();
            httpRequest += httpRequestLine + "\n";
            getContentLength(httpRequestLine);
        }
    }

    private void getContentLength(String httpRequestLine) {
        if(httpRequestLine.contains("Content-Length")) {
            bodyContentLength = Integer.parseInt(httpRequestLine.split(": ")[1]);
            requestBodyBuffer = new char[bodyContentLength];
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
