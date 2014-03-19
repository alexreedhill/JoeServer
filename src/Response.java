import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private Request request;
    private FileReader fileReader;
    private String statusCode;
    private byte[] body;
    private static final Map<String, String> STATUS_MESSAGES = createStatusMessages();

    private static Map<String, String> createStatusMessages() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("200", "OK");
        result.put("404", "Not Found");
        return Collections.unmodifiableMap(result);
    }

    public Response(Request request) throws IOException {
        this.request = request;
        fileReader = new FileReader();
        try {
            body = fileReader.read(request.path);
            statusCode = "200";
        } catch(IOException ex) {
            statusCode = "404";
        }
    }

    public byte[] respond() throws IOException {
        if(request.path.equals("/")) {
            return (request.httpVersion + " 200 OK\r\n").getBytes();
        } else if(statusCode.equals("404")) {
            return (request.httpVersion + " 404 Not Found\r\n").getBytes();
        } else {
            return buildFullResponse();
        }
    }

    private byte[] buildFullResponse() throws IOException {
        byte[] metadata = (buildStatusLine() + buildContentTypeHeader()).getBytes();
        byte[] fullResponse = new byte[metadata.length + body.length];
        System.arraycopy(metadata, 0, fullResponse, 0, metadata.length);
        System.arraycopy(body, 0, fullResponse, metadata.length, body.length);
        return fullResponse;
    }

    private String buildStatusLine() {
        return request.httpVersion + " " + statusCode + " " + getStatusMessage() + "\r\n";
    }

    public String getStatusMessage() {
        return STATUS_MESSAGES.get(statusCode);
    }

    private String buildContentTypeHeader() throws IOException {
        return "Content-Type: " + fileReader.getMimeType(request.path) + "\r\n\n";
    }

}