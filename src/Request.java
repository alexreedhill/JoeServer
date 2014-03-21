public class Request {
    public String method;
    public String path;
    public String httpVersion;

    public Request(String rawRequest) throws Exception {
        System.out.println("Request instantiated: " + rawRequest);
        processRawRequest(rawRequest);
    }

    private void processRawRequest(String rawRequest) throws Exception {
        String[] splitRawRequest = rawRequest.split(" ");
        path = splitRawRequest[1];
        httpVersion = splitRawRequest[2];
        method = path.equals("/redirect") ? "REDIRECT" : splitRawRequest[0];
    }
}
