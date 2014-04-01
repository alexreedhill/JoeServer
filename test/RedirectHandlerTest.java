import Routing.RedirectHandler;
import Request.Request;
import Request.RequestBuilder;
import Response.Response;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class RedirectHandlerTest {

    @Test
    public void redirectsToRoot() throws Exception{
        RequestBuilder builder = new RequestBuilder("GET /redirect HTTP/1.0");
        Request request = builder.build();
        RedirectHandler redirectHandler = new RedirectHandler(request);
        Response response = redirectHandler.handle();
        String fullResponse = response.convertToString();
        assertEquals("HTTP/1.0 307 Moved Temporarily\r\nLocation: http://localhost:5000/\r\n\n", fullResponse);
    }
}
