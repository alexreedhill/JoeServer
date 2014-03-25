import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PutHandlerTest {
    private Response response;
    private PutHandler handler;
    private RequestBuilder builder;

    @Before
    public void setUpBuilder() {
        builder = new RequestBuilder();
    }

    @Test
    public void itReturnsMethodNotAllowed() throws Exception {
        Request request = builder.build("PUT /file1 HTTP/1.1");
        handler = new PutHandler(request);
        response = handler.handle();
        assertEquals("405", response.statusCode);
    }

}
