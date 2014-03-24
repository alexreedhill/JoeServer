import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
    private String rawRequest;
    public String method;
    public String path;
    public String httpVersion;
    public LinkedHashMap<String, String> params;
    private String rawParams;
    public LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();

    public Request(String rawRequest) throws Exception {
        System.out.println("Request instantiated: " + rawRequest);
        this.rawRequest = rawRequest;
        processRawRequest();
    }

    private void processRawRequest() throws Exception {
        parseFirstLine();
        parseHeaders();
        parseParams();
    }

    private void parseFirstLine() {
        String[] splitRawRequest = rawRequest.split(" ");
        splitPathFromParams(splitRawRequest[1]);
        httpVersion = splitRawRequest[2];
        method = path.equals("/redirect") ? "REDIRECT" : splitRawRequest[0];
    }

    private void parseHeaders() {
        try {
            String rawHeaders = rawRequest.split("\r\n")[1];
            String[] splitHeaders = rawHeaders.split("\n");
            for (String header : splitHeaders) {
                String[] splitHeader = header.split(":");
                headers.put(splitHeader[0].trim(), splitHeader[1].trim());
            }
        } catch(ArrayIndexOutOfBoundsException ex) {}
    }

    private void parseParams() {
        ParameterDecoder decoder = new ParameterDecoder(rawParams);
        params = decoder.decode();
    }

    private void splitPathFromParams(String fullURL) {
        try {
            String[] splitURL = fullURL.split("[?]");
            path = splitURL[0];
            rawParams = splitURL[1];
        } catch(ArrayIndexOutOfBoundsException ex) {
            path = fullURL;
            rawParams = "";
        }
    }

    public String convertParamsToString() {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry entry : params.entrySet()) {
            builder = buildString(builder, entry);
        }
        return builder.toString();
    }

    private StringBuilder buildString(StringBuilder builder, Map.Entry entry) {
        builder.append(entry.getKey());
        builder.append(" = ");
        builder.append(entry.getValue());
        builder.append("\n");
        return builder;
    }

    public byte[] convertParamsToBytes() {
        return convertParamsToString().getBytes();
    }
}
