import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import org.apache.commons.codec.binary.Base64;

public class BasicAuthenticatorTest {
    private Response response;
    private GetHandler handler;
    private RequestBuilder builder;

    @Before
    public void setupBuilder() {
        builder  = new RequestBuilder();
    }

    @Test
    public void addsCorrectStatusAndHeadersToResponseOnUnauthenticatedRequest() throws Exception {
        Request request = builder.build("GET /logs HTTP/1.1");
        handler = new GetHandler(request);
        response = handler.handle();
        String stringResponse = response.convertToString();
        assertEquals("HTTP/1.1 401 Unauthorized\r\nWWW-Authenticate: Basic realm=\"JoeServer\"\r\n\nAuthentication required", stringResponse);
    }

    @Test public void addsCorrectStatusAndHeadersToResponseOnAuthenticatedRequest() throws Exception {
        byte[] bytes = "admin:hunter2".getBytes();
        String encodedAuthorizationString = Base64.encodeBase64String(bytes);
        Request request = builder.build("GET /logs HTTP/1.1\r\nAuthorization: Basic " + encodedAuthorizationString);
        handler = new GetHandler(request);
        response = handler.handle();
        String stringResponse = response.convertToString();
        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\nGET /log HTTP/1.1\nPUT /these HTTP/1.1\nHEAD /requests HTTP/1.1", stringResponse);
    }

}
