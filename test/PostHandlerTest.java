import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PostHandlerTest {
    private PostHandler handler;
    private Response response;
    private RequestBuilder builder;

    @Before
    public void createRequestBuilder() {
        builder = new RequestBuilder();
    }

    @Test
    public void itReturnsMethodNotAllowed() throws Exception {
        Request request = builder.build("POST /text-file.txt HTTP/1.1");
        handler = new PostHandler(request);
        response = handler.handle();
        assertEquals("405", response.statusCode);
    }
}
