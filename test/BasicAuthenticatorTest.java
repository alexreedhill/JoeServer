import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import org.apache.commons.codec.binary.Base64;

public class BasicAuthenticatorTest {
    private BasicAuthenticator auth;
    private Response response;
    private GetHandler handler;

    @Test
    public void addsCorrectStatusAndHeadersToResponseOnUnauthenticatedRequest() throws Exception {
        Request request = new Request("GET /logs HTTP/1.1");
        handler = new GetHandler(request);
        response = handler.handle();
        String stringResponse = response.convertToString();
        assertEquals("HTTP/1.1 401 Unauthorized\r\nWWW-Authenticate: Basic realm=\"JoeServer\"\r\n\nAuthentication required", stringResponse);
    }

    @Test public void addsCorrectStatusAndHeadersToResponseOnAuthenticatedRequest() throws Exception {
        byte[] bytes = "admin:hunter2".getBytes();
        String encodedAuthorizationString = Base64.encodeBase64String(bytes);
        Request request = new Request("GET /logs HTTP/1.1\r\nAuthorization: Basic " + encodedAuthorizationString);
        handler = new GetHandler(request);
        response = handler.handle();
        String stringResponse = response.convertToString();
        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\nGET /log HTTP/1.1\nPUT /these HTTP/1.1\nHEAD /requests HTTP/1.1", stringResponse);
    }

}
