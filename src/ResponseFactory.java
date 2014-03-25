import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseFactory {
    private Response response;
    private Request request;
    private String statusLine;
    private static final Map<String, String> STATUS_MESSAGES = createStatusMessages();
    private String headerLines = "";
    private FileReader fileReader = new FileReader();

    public ResponseFactory(Response response) throws Exception {
        this.response = response;
        this.request = response.request;
    }

    public Response buildFullResponse() throws Exception {
        buildStatusLine();
        buildHeaders();
        byte[] metadata = (statusLine + headerLines).getBytes();
        byte[] fullResponse = new byte[metadata.length + response.body.length];
        System.arraycopy(metadata, 0, fullResponse, 0, metadata.length);
        System.arraycopy(response.body, 0, fullResponse, metadata.length, response.body.length);
        response.fullResponse = fullResponse;
        return response;
    }

    private void buildStatusLine() {
        statusLine = request.httpVersion + " " + response.statusCode + " " + getStatusMessage() + "\r\n";
    }

    private static Map<String, String> createStatusMessages() {
        Map<String, String> messages = new HashMap<String, String>();
        messages.put("200", "OK");
        messages.put("206", "Partial Content");
        messages.put("404", "Not Found");
        messages.put("307", "Moved Temporarily");
        messages.put("401", "Unauthorized");
        messages.put("405", "Method Not Allowed");
        return Collections.unmodifiableMap(messages);
    }

    private String getStatusMessage() {
        return STATUS_MESSAGES.get(response.statusCode);
    }

    private void buildHeaders() throws Exception {
        buildContentTypeHeader();
        int i = 1;
        for(Map.Entry entry : response.headers.entrySet()) {
            buildHeader(entry, i);
            i++;
        }
    }

    private void buildContentTypeHeader() throws IOException {
        if(response.statusCode.equals("206") || request.method.equals("GET") && response.statusCode.equals("200")) {
            String mimeType = fileReader.getMimeType(request.path);
            response.headers.put("Content-Type", mimeType);
        }
    }

    private void buildHeader(Map.Entry entry, int i) {
        headerLines += entry.getKey() + ": " + entry.getValue();
        headerLines += i < response.headers.size() ? "\n" : "\r\n\n";
    }
}
