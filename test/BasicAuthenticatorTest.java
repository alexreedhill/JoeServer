import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class BasicAuthenticatorTest {
    private BasicAuthenticator auth;
    private Request request;
    private Response response;

    @Before
    public void setUpRequest() throws Exception {
        request = new Request("GET /logs HTTP/1.1");
        response = new Response(request);
        auth = new BasicAuthenticator(response);
    }

    @Test
    public void addsCorrectStatusAndHeadersToResponseOnUnauthenticatedRequest() throws Exception {
        response = auth.authenticate();
        String stringResponse = response.convertToString();
        assertEquals("HTTP/1.1 401 Unauthorized\r\nWWW-Authenticate: Basic realm=\"JoeServer\"\r\n\n", stringResponse);
    }

}
