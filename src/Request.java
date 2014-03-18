/**
 * Created by alexhill on 3/17/14.
 */
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
        this.method = splitRawRequest[0];
        this.path = splitRawRequest[1];
        this.httpVersion = splitRawRequest[2];
    }
}
