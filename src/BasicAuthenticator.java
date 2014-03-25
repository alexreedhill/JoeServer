import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;

public class BasicAuthenticator {
    private Request request;
    private Response response;
    private String decodedAuthHeader;


    public BasicAuthenticator(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Response authenticate() throws IOException {
        try {
            decodeAuthHeader();
            checkDecodedAuthHeader();
        } catch(NullPointerException ex) {
            set401Response();
        }
        return response;
    }

    private void decodeAuthHeader() throws UnsupportedEncodingException {
        String authHeader = request.headers.get("Authorization");
        try {
            String encodedString = authHeader.split(" ")[1];
            byte[] bytes = Base64.decodeBase64(encodedString);
            decodedAuthHeader = new String(bytes, "UTF-8");
        } catch(ArrayIndexOutOfBoundsException ex) { }
    }

    private void checkDecodedAuthHeader() {
        if(decodedAuthHeader.equals("admin:hunter2")) {
            response.statusCode = "200";
            response.body = "GET /log HTTP/1.1\nPUT /these HTTP/1.1\nHEAD /requests HTTP/1.1".getBytes();
        } else {
            set401Response();
        }
    }

    private void set401Response() {
        response.statusCode = "401";
        response.setHeader("WWW-Authenticate", "Basic realm=\"JoeServer\"");
        response.body = "Authentication required".getBytes();
    }
}
