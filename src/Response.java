import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Response {
    public Request request;
    private FileReader fileReader;
    private String statusCode;
    private byte[] body = new byte[0];
    private static final Map<String, String> STATUS_MESSAGES = createStatusMessages();
    private Map<String, String> HEADERS = new HashMap<String, String>();

    private static Map<String, String> createStatusMessages() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("200", "OK");
        result.put("404", "Not Found");
        result.put("301", "Moved Permanently");
        return Collections.unmodifiableMap(result);
    }

    public Response(Request request) throws IOException {
        this.request = request;
        fileReader = new FileReader();
    }

    public void openResource() throws IOException {
        set200Response();
        body = fileReader.read(request.path);
    }

    public void set200Response() throws IOException {
        statusCode = "200";
    }

    public void set404Response() throws IOException {
        statusCode = "404";
    }

    public void set301Response() throws IOException {
        statusCode = "301";
        setHeader("Location", "http://localhost:5000/");
    }

    public void setHeader(String header, String value) {
        HEADERS.put(header, value);
    }

    public byte[] respond() throws Exception {
        byte[] metadata = (buildStatusLine() + buildHeaders()).getBytes();
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

    private String buildHeaders() throws Exception {
        String headers = "";
        buildContentTypeHeader();
        int i = 0;
        for(Map.Entry entry : HEADERS.entrySet()) {
            headers += entry.getKey() + ": " + entry.getValue();
            i++;
            if(i < HEADERS.size()) {
                headers += "\n";
            } else {
                headers += "\r\n\n";
            }
        }
        return headers;
    }

    private void buildContentTypeHeader() throws IOException {
        if(request.method.equals("GET") && statusCode.equals("200")) {
            String mimeType = fileReader.getMimeType(request.path);
            HEADERS.put("Content-Type", mimeType);
        } else {
            HEADERS.put("Content-Type", "text/plain");
        }
    }

    public String convertToString() throws Exception {
        return new String(respond());
    }
}