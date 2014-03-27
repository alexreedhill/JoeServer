import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PutHandlerTest {

    @Test
    public void itReturnsMethodNotAllowed() throws Exception {
        RequestBuilder builder = new RequestBuilder("PUT /file1 HTTP/1.1");
        Request request = builder.build();
        PutHandler handler = new PutHandler(request);
        Response response = handler.handle();
        assertEquals("405", response.statusCode);
    }

}
