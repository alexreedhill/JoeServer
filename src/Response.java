import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Response {
    public Request request;
    private String statusCode;
    private byte[] body = new byte[0];
    private static final Map<String, String> STATUS_MESSAGES = createStatusMessages();
    private Map<String, String> HEADERS = new HashMap<String, String>();
    private String headers = "";
    private FileReader fileReader = new FileReader();

    private static Map<String, String> createStatusMessages() {
        Map<String, String> messages = new HashMap<String, String>();
        messages.put("200", "OK");
        messages.put("404", "Not Found");
        messages.put("301", "Moved Permanently");
        return Collections.unmodifiableMap(messages);
    }

    public Response(Request request) throws IOException {
        this.request = request;
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

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setHeader(String header, String value) {
        HEADERS.put(header, value);
    }

    public String getHeaderValue(String header) {
        return HEADERS.get(header);
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
        buildContentTypeHeader();
        int i = 1;
        for(Map.Entry entry : HEADERS.entrySet()) {
            buildHeader(entry, i);
            i++;
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

    private void buildHeader(Map.Entry entry, int i) {
        headers += entry.getKey() + ": " + entry.getValue();
        if(i < HEADERS.size()) {
            headers += "\n";
        } else {
            headers += "\r\n\n";
        }
    }

    public String convertToString() throws Exception {
        return new String(respond());
    }
}