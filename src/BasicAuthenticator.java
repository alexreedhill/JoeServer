import java.io.IOException;
import org.apache.commons.codec.BinaryDecoder;

public class BasicAuthenticator {
    private Request request;
    private Response response;


    public BasicAuthenticator(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Response authenticate() throws IOException {
        response.setHeader("WWW-Authenticate", "Basic realm=\"JoeServer\"");
//        if(request)
        response.statusCode = "401";
        response.body = "Authentication required".getBytes();
        return response;
    }
}
