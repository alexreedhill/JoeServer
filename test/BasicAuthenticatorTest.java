import Request.Request;
import Request.RequestBuilder;
import Response.Response;
import Routing.GetHandler;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BasicAuthenticatorTest {
    private Response response;
    private GetHandler handler;
    private RequestBuilder builder;

    @Test
    public void addsCorrectStatusAndHeadersToResponseOnUnauthenticatedRequest() throws Exception {
        builder  = new RequestBuilder("GET /logs HTTP/1.1");
        Request request = builder.build();
        handler = new GetHandler(request);
        response = handler.handle();
        String stringResponse = response.convertToString();
        assertEquals("HTTP/1.1 401 Unauthorized\r\nWWW-Authenticate: Basic realm=\"JoeServer\"\r\n\nAuthentication required", stringResponse);
    }

    @Test public void addsCorrectStatusAndHeadersToResponseOnAuthenticatedRequest() throws Exception {
        byte[] bytes = "admin:hunter2".getBytes();
        String encodedAuthorizationString = Base64.encodeBase64String(bytes);
        builder = new RequestBuilder("GET /logs HTTP/1.1\r\nAuthorization: Basic " + encodedAuthorizationString);
        Request request = builder.build();
        handler = new GetHandler(request);
        response = handler.handle();
        String stringResponse = response.convertToString();
        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\nGET /log HTTP/1.1\nPUT /these HTTP/1.1\nHEAD /requests HTTP/1.1", stringResponse);
    }

}
