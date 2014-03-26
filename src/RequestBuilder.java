import java.util.Map;

public class RequestBuilder {
    private String rawRequest;
    private String rawParams;
    private Request request;

    public RequestBuilder() {
        this.request = new Request();
    }

    public Request build(String rawRequest) throws Exception {
        this.rawRequest = rawRequest;
        System.out.println("Request instantiated: " + rawRequest);
        parseFirstLine();
        parseHeaders();
        parseParams();
        buildParamsString();
        return request;
    }

    private void parseFirstLine() {
        String[] splitRawRequest = rawRequest.split("\r\n");
        String statusLine = splitRawRequest[0];
        String[] splitStatusLine = statusLine.split(" ");
        try {
            splitPathFromParams(splitStatusLine[1]);
        } catch(ArrayIndexOutOfBoundsException ex) {
            request.path = splitStatusLine[1];
            rawParams = "";
        }
        request.httpVersion = splitStatusLine[2];
        request.method = request.path.equals("/redirect") ? "REDIRECT" : splitStatusLine[0];
    }

    private void parseHeaders() {
        try {
            String rawHeaders = rawRequest.split("\r\n")[1];
            String[] splitHeaders = rawHeaders.split("\n");
            for (String header : splitHeaders) {
                String[] splitHeader = header.split(":");
                request.headers.put(splitHeader[0].trim(), splitHeader[1].trim());
            }
        } catch(ArrayIndexOutOfBoundsException ex) {}
    }

    private void parseParams() {
        ParameterDecoder decoder = new ParameterDecoder(rawParams);
        request.paramsHash = decoder.decode();
    }

    private void splitPathFromParams(String fullUrl) {
        String[] splitURL = fullUrl.split("[?]");
        request.path = splitURL[0];
        rawParams = splitURL[1];
    }

    public void buildParamsString() {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry entry : request.paramsHash.entrySet()) {
            builder = buildParamsString(builder, entry);
        }
        request.params = builder.toString();
    }

    private StringBuilder buildParamsString(StringBuilder builder, Map.Entry entry) {
        builder.append(entry.getKey());
        builder.append(" = ");
        builder.append(entry.getValue());
        builder.append("\n");
        return builder;
    }
}
