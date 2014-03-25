import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private Response response;
    private Request request;
    private String statusLine;
    private static final Map<String, String> STATUS_MESSAGES = createStatusMessages();
    private String headerLines = "";
    private FileReader fileReader = new FileReader();

    public ResponseBuilder(Request request) throws Exception {
        response = new Response(request);
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

    public void buildOKResponse() {
        response.statusCode = "200";
    }

    public void buildAuthenticatedResponse() throws IOException {
        BasicAuthenticator auth = new BasicAuthenticator(response);
        response = auth.authenticate();
    }

    public void buildParameterResponse() {
        response.statusCode = "200";
        response.body = request.convertParamsToBytes();
    }

    public void buildOptionsResponse() {
        response.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        response.statusCode = "200";
    }

    public void buildFileResponse() throws Exception {
        try {
            openResource();
            String rangeHeader;
            if((rangeHeader = request.headers.get("Range")) != null ) {
                buildPartialContentResponse(rangeHeader);
            } else {
                response.statusCode = "200";
            }
        } catch(IOException ex) {
            response.statusCode = "404";
        }
    }

    private void openResource() throws IOException {
        response.body = fileReader.read(request.path);
    }

    private void buildPartialContentResponse(String rangeHeader) throws Exception {
        response.statusCode = "206";
        String[] splitRangeHeader = rangeHeader.split("-");
        int start = Integer.parseInt(splitRangeHeader[0].replace("bytes=", ""));
        int length = Integer.parseInt(splitRangeHeader[1]);
        response.setHeader("Content-Range", "bytes " + start + "-" + length + "/" + response.body.length);
        byte[] partialContent = new byte[length];
        System.arraycopy(response.body, start, partialContent, 0, length);
        response.body = partialContent;
    }

    public void buildRedirectResponse() {
        response.statusCode = "307";
        response.setHeader("Location", "http://localhost:5000/");
    }

    public void buildMethodNotAllowedResponse() {
        response.statusCode = "405";
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
