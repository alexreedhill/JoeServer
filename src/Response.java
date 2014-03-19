import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Response {
    public Request request;
    private FileReader fileReader = new FileReader();
    private String statusCode;
    private byte[] body = new byte[0];
    private String contentTypeHeader;
    private RequestHandler handler;
    private static final Map<String, String> STATUS_MESSAGES = createStatusMessages();

    private static Map<String, String> createStatusMessages() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("200", "OK");
        result.put("404", "Not Found");
        return Collections.unmodifiableMap(result);
    }

    public Response(Request request) throws IOException {
        this.request = request;
        if(request.method.equals("POST")) {
            handler = new PostHandler();
        } else if(request.method.equals("GET")) {
            handler = new GetHandler();
        }
        try {
            handler.handle(this);
        } catch(NullPointerException ex) {
            System.out.println("No handler yet for this type of request: " + request.method);
        }
    }

    public void openResource() throws IOException {
        body = fileReader.read(request.path);
        set200Response();
    }

    public void set200Response() throws IOException {
        statusCode = "200";
    }

    public void set404Response() throws IOException {
        statusCode = "404";
    }

    public byte[] respond() throws IOException {
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
        if(request.method.equals("GET") && statusCode.equals("200")) {
            String mimeType = fileReader.getMimeType(request.path);
            return "Content-Type: " + mimeType + "\r\n\n";
        } else {
            return "";
        }
    }
}