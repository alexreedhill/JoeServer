import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PostHandlerTest {

    @Test
    public void itReturnsMethodNotAllowed() throws Exception {
        RequestBuilder builder = new RequestBuilder("POST /text-file.txt HTTP/1.1");
        Request request = builder.build();
        PostHandler handler = new PostHandler(request);
        Response response = handler.handle();
        assertEquals("405", response.statusCode);
    }
}
