import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private Response response;
    private Request request;
    private FileReader fileReader;
    private PageGenerator generator;

    public ResponseBuilder(Request request) throws Exception {
        response = new Response(request);
        this.request = response.request;
        this.fileReader = new FileReader(request);
        generator = new DirectoryPageGenerator(fileReader);
    }

    public Response buildFullResponse() throws Exception {
        String statusLine = buildStatusLine();
        String headerLines = buildHeaders();
        byte[] metadata = (statusLine + headerLines).getBytes();
        byte[] fullResponse = new byte[metadata.length + response.body.length];
        System.arraycopy(metadata, 0, fullResponse, 0, metadata.length);
        System.arraycopy(response.body, 0, fullResponse, metadata.length, response.body.length);
        response.fullResponse = fullResponse;
        return response;
    }

    private String buildStatusLine() {
        return request.httpVersion + " " + response.statusCode + " " + getStatusMessage() + "\r\n";
    }

    private String getStatusMessage() {
        Map<String, String> STATUS_MESSAGES = createStatusMessages();
        return STATUS_MESSAGES.get(response.statusCode);
    }

    private String buildHeaders() throws Exception {
        int i = 1;
        String headerLines = "";
        for(Map.Entry entry : response.headers.entrySet()) {
            headerLines += buildHeader(entry, i);
            i++;
        }
        return headerLines;
    }

    private String buildHeader(Map.Entry entry, int i) {
        String headerLines = "";
        headerLines += entry.getKey() + ": " + entry.getValue();
        headerLines += i < response.headers.size() ? "\n" : "\r\n\n";
        return headerLines;
    }

    public void buildOKResponse() {
        response.statusCode = "200";
    }

    public void buildParameterResponse() throws Exception {
        response.statusCode = "200";
        buildContentTypeHeader();
        response.body = request.convertParamsToBytes();
    }

    private void buildContentTypeHeader() throws IOException {
        String mimeType = fileReader.getMimeType(request.path);
        response.headers.put("Content-Type", mimeType);
    }

    public void buildOptionsResponse() {
        response.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        response.statusCode = "200";
    }

    public void buildFileResponse() throws Exception {
        try {
            openResource();
            buildContentTypeHeader();
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
        response.body = fileReader.read();
    }

    private void buildPartialContentResponse(String rangeHeader) throws Exception {
        response.statusCode = "206";
        String[] splitRangeHeader = rangeHeader.split("-");
        int start = Integer.parseInt(splitRangeHeader[0].replace("bytes=", ""));
        int length = Integer.parseInt(splitRangeHeader[1]);
        setPartialContentHeaders(splitRangeHeader, start, length);
        setPartialContentBody(start, length);
    }

    private void setPartialContentHeaders(String[] splitRangeHeader, int start, int length) {
        response.setHeader("Content-Length", splitRangeHeader[1]);
        response.setHeader("Content-Range", "bytes " + start + "-" + length + "/" + response.body.length);
    }

    private void setPartialContentBody(int start, int length) {
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

    public void buildDirectoryResponse() throws Exception {
        response.statusCode = "200";
        response.headers.put("Content-Type", "text/html");
        response.body = generator.generate();
    }

    public Response buildAuthenticatedResponse() throws IOException {
        response.statusCode = "200";
        buildContentTypeHeader();
        response.body = "GET /log HTTP/1.1\nPUT /these HTTP/1.1\nHEAD /requests HTTP/1.1".getBytes();
        return response;
    }

    public Response buildAuthenticationRequiredResponse() {
        response.statusCode = "401";
        response.setHeader("WWW-Authenticate", "Basic realm=\"JoeServer\"");
        response.body = "Authentication required".getBytes();
        return response;
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
}
