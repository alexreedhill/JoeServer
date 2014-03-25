import java.util.HashMap;
import java.util.Map;

public class Response {
    public String statusCode;
    public Map<String, String> headers = new HashMap<String, String>();
    public byte[] body = new byte[0];
    public byte[] fullResponse;
    public Request request;

    public Response(Request request) {
        this.request = request;
    }

    public void setHeader(String header, String value) {
        headers.put(header, value);
    }

    public String getHeaderValue(String header) {
        return headers.get(header);
    }

    public String convertToString() throws Exception {
        return new String(fullResponse);
    }
}