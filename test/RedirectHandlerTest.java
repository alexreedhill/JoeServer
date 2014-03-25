import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class RedirectHandlerTest {
    private RedirectHandler redirectHandler;
    private ResponseFactory factory;

    @Before
    public void setUpRequest() throws Exception {
        Request request = new Request("GET /redirect HTTP/1.0");
        redirectHandler = new RedirectHandler(request);
    }

    @Test
    public void redirectsToRoot() throws Exception{
        Response response = redirectHandler.handle();
        factory = new ResponseFactory(response);
        factory.buildFullResponse();
        String fullResponse = response.convertToString();
        assertEquals("HTTP/1.0 307 Moved Temporarily\r\nLocation: http://localhost:5000/\r\n\n", fullResponse);
    }
}
