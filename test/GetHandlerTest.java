import Routing.GetHandler;
import Request.Request;
import Request.RequestBuilder;
import Response.Response;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

public class GetHandlerTest {
    private Request request;
    private GetHandler handler;
    private RequestBuilder builder;

    @Test
    public void recognizesRestrictedRoute() throws Exception {
        builder = new RequestBuilder("GET /logs HTTP/1.1");
        request = builder.build();
        handler = new GetHandler(request);
        Boolean result = handler.restrictedRoute();
        assertTrue(result);
    }

    @Test
    public void returnsPartialContentStatusCode() throws Exception {
        builder = new RequestBuilder("GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-4");
        request = builder.build();
        handler = new GetHandler(request);
        Response response = handler.handle();
        assertEquals("HTTP/1.1 206 Partial Content\r\nContent-Length: 4\n" +
                     "Content-Type: text/plain\nContent-Range: bytes 0-4/77\r\n\nThis", response.convertToString());
    }


}
