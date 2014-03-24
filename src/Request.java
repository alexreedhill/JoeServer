import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
    public String method;
    public String path;
    public String httpVersion;
    public LinkedHashMap<String, String> params;
    private String rawParams;

    public Request(String rawRequest) throws Exception {
        System.out.println("Request instantiated: " + rawRequest);
        processRawRequest(rawRequest);
    }

    private void processRawRequest(String rawRequest) throws Exception {
        String[] splitRawRequest = rawRequest.split(" ");
        splitPathFromParams(splitRawRequest[1]);
        httpVersion = splitRawRequest[2];
        method = path.equals("/redirect") ? "REDIRECT" : splitRawRequest[0];
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
            builder.append(entry.getKey() + " = " + entry.getValue() + "\n");
        }
        return builder.toString();
    }
}
