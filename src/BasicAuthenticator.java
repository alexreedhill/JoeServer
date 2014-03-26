import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;

public class BasicAuthenticator {
    private Request request;
    private ResponseBuilder builder;
    private String decodedAuthHeader;

    public BasicAuthenticator(Request request) throws Exception {
        this.request = request;
        builder = new ResponseBuilder(request);
    }

    public ResponseBuilder authenticate() throws Exception {
        try {
            decodeAuthHeader();
            checkDecodedAuthHeader();
        } catch(NullPointerException ex) {
            builder.buildAuthenticationRequiredResponse();
        }
        return builder;
    }

    private void decodeAuthHeader() throws UnsupportedEncodingException {
        String authHeader = request.headers.get("Authorization");
        try {
            String encodedString = authHeader.split(" ")[1];
            byte[] bytes = Base64.decodeBase64(encodedString);
            decodedAuthHeader = new String(bytes, "UTF-8");
        } catch(ArrayIndexOutOfBoundsException ex) { }
    }

    private void checkDecodedAuthHeader() throws Exception {
        if(decodedAuthHeader.equals("admin:hunter2")) {
            builder.buildAuthenticatedResponse();
        } else {
            builder.buildAuthenticationRequiredResponse();
        }
    }
}
