import org.junit.Test;
import static junit.framework.Assert.assertEquals;

/**
 * Created by alexhill on 3/19/14.
 */
public class RedirectHandlerTest {
    private RedirectHandler redirectHandler = new RedirectHandler();
    private Response response;

    @Test
    public void redirectsToRoot() throws Exception{
        Request request = new Request("GET /redirect HTTP/1.0");
        response = redirectHandler.handle(request);
        String fullResponse = response.convertToString();
        assertEquals("HTTP/1.0 301 Moved Permanently\r\nLocation: http://localhost:5000/\nContent-Type: text/plain\r\n\n", fullResponse);
    }
}
