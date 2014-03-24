import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Response {
    public String statusCode;
    public byte[] body = new byte[0];
    public static final Map<String, String> STATUS_MESSAGES = createStatusMessages();
    private Request request;
    private String statusLine;
    private String headerLines = "";
    private Map<String, String> headers = new HashMap<String, String>();
    private FileReader fileReader = new FileReader();

    private static Map<String, String> createStatusMessages() {
        Map<String, String> messages = new HashMap<String, String>();
        messages.put("200", "OK");
        messages.put("404", "Not Found");
        messages.put("307", "Moved Temporarily");
        messages.put("401", "Unauthorized");
        return Collections.unmodifiableMap(messages);
    }

    public Response(Request request) throws IOException {
        this.request = request;
    }

    public void setHeader(String header, String value) {
        headers.put(header, value);
    }

    public String getHeaderValue(String header) {
        return headers.get(header);
    }

    public String convertToString() throws Exception {
        return new String(respond());
    }

    public byte[] respond() throws Exception {
        buildHeaders();
        buildStatusLine();
        byte[] metadata = (statusLine + headerLines).getBytes();
        byte[] fullResponse = new byte[metadata.length + body.length];
        System.arraycopy(metadata, 0, fullResponse, 0, metadata.length);
        System.arraycopy(body, 0, fullResponse, metadata.length, body.length);
        return fullResponse;
    }

    private void buildStatusLine() {
        statusLine = request.httpVersion + " " + statusCode + " " + getStatusMessage() + "\r\n";
    }

    private String getStatusMessage() {
        return STATUS_MESSAGES.get(statusCode);
    }

    private void buildHeaders() throws Exception {
        buildContentTypeHeader();
        int i = 1;
        for(Map.Entry entry : headers.entrySet()) {
            buildHeader(entry, i);
            i++;
        }
    }

    private void buildContentTypeHeader() throws IOException {
        if(request.method.equals("GET") && statusCode.equals("200")) {
            String mimeType = fileReader.getMimeType(request.path);
            headers.put("Content-Type", mimeType);
        }
    }

    private void buildHeader(Map.Entry entry, int i) {
        headerLines += entry.getKey() + ": " + entry.getValue();
        headerLines += i < headers.size() ? "\n" : "\r\n\n";
    }
}