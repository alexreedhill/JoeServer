import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PostHandlerTest {
    private PostHandler handler;
    private Response response;

    @Test
    public void itReturnsMethodNotAllowed() throws Exception {
        Request request = new Request("POST /text-file.txt HTTP/1.1");
        handler = new PostHandler(request);
        response = handler.handle();
        assertEquals("405", response.statusCode);
    }
}
