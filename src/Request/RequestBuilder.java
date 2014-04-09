package Request;

import Util.ParameterDecoder;

import java.util.Map;

public class RequestBuilder {
    private String httpRequest;
    private String httpParams;
    private Request request;

    public RequestBuilder(String httpRequest) {
        this.request = new Request();
        this.httpRequest = httpRequest;
        request.publicPath = "../cob_spec/public";
        System.out.println("Server.Request instantiated: " + httpRequest);
    }

    public RequestBuilder(String httpRequest, String publicPath) {
        this.request = new Request();
        this.httpRequest = httpRequest;
        request.publicPath = publicPath;
        System.out.println("Server.Request instantiated: " + httpRequest);
    }

    public Request build() throws ArrayIndexOutOfBoundsException {
        String[] splitHttpRequest = httpRequest.split("\r\n");
        parseFirstLine(splitHttpRequest[0]);
        decodeParams();
        parseHeaders();
        buildHeadersAndBody(splitHttpRequest);
        return request;
    }

    private void parseFirstLine(String statusLine) {
        String[] splitStatusLine = statusLine.split(" ");
        parsePath(splitStatusLine[1]);
        request.httpVersion = splitStatusLine[2];
        request.method = request.path.equals("/redirect") ? "REDIRECT" : splitStatusLine[0];
    }

    private void parsePath(String path) {
        try {
            splitPathFromParams(path);
        } catch(ArrayIndexOutOfBoundsException ex) {
            request.path = path;
            httpParams = "";
        }
    }

    private void splitPathFromParams(String fullUrl) {
        String[] splitURL = fullUrl.split("[?]");
        request.path = splitURL[0];
        httpParams = splitURL[1];
    }

    private void parseHeaders() throws ArrayIndexOutOfBoundsException {
        try {
            String rawHeaders = httpRequest.split("\r\n")[1];
            String[] splitHeaders = rawHeaders.split("\n");
            splitEachHeader(splitHeaders);
        } catch(ArrayIndexOutOfBoundsException ignored) {}
    }

    private void splitEachHeader(String[] splitHeaders) {
        for (String header : splitHeaders) {
            String[] splitHeader = header.split(":");
            request.headers.put(splitHeader[0].trim(), splitHeader[1].trim());
        }
    }

    private void buildHeadersAndBody(String[] splitHttpRequest) {
        try {
            String[] headersAndBody = splitHttpRequest[splitHttpRequest.length - 1].split("\n");
            request.body = headersAndBody[headersAndBody.length -1];
        } catch(ArrayIndexOutOfBoundsException ignored) {}
    }

    private void decodeParams() {
        ParameterDecoder decoder = new ParameterDecoder(httpParams);
        request.paramsHash = decoder.decode();
        StringBuilder builder = new StringBuilder();
        for(Map.Entry entry : request.paramsHash.entrySet())
            builder = buildParams(builder, entry);
        request.params = builder.toString();
    }

    private StringBuilder buildParams(StringBuilder builder, Map.Entry entry) {
        builder.append(entry.getKey());
        builder.append(" = ");
        builder.append(entry.getValue());
        builder.append("\n");
        return builder;
    }

}
