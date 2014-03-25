import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

public class GetHandlerTest {
    private Request request;
    private GetHandler handler;
    private ResponseFactory factory;


    @Test
    public void recognizesRestrictedRoute() throws Exception {
        request = new Request("GET /logs HTTP/1.1");
        handler = new GetHandler(request);
        Boolean result = handler.restrictedRoute();
        assertTrue(result);
    }

    @Test
    public void returnsPartialContentStatusCode() throws Exception {
        request = new Request("GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-4");
        handler = new GetHandler(request);
        Response response = handler.handle();
        factory = new ResponseFactory(response);
        factory.buildFullResponse();
        assertEquals("HTTP/1.1 206 Partial Content\r\nContent-Type: text/plain\nContent-Range: bytes 0-4/77\r\n\nThis", response.convertToString());
    }


}
