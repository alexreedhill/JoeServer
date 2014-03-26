import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class ResponseTest {
    private Request request;
    private Dispatcher dispatcher = new Dispatcher();
    private Response response;
    private RequestBuilder builder;

    @Before
    public void setUpBuilder() {
        builder = new RequestBuilder();
    }

    @Test
    public void returnsResponseWithFileContentsAndStatusCode() throws Exception {
        request = builder.build("GET /file1 HTTP/1.0");
        response = dispatcher.dispatch(request);
        String fullResponse = response.convertToString();
        assertEquals("HTTP/1.0 200 OK\r\nContent-Type: text/plain\r\n\nfile1 contents", fullResponse);
    }

    @Test
    public void returns404ResponseWhenNoFileLocated() throws Exception {
        request = builder.build("GET /foobar HTTP/1.0");
        response = dispatcher.dispatch(request);
        String fullResponse = response.convertToString();
        assertEquals("HTTP/1.0 404 Not Found\r\n", fullResponse);
    }

    @Test
    public void returns200OnPostRequest() throws Exception {
        request = builder.build("POST /form HTTP/1.1");
        response = dispatcher.dispatch(request);
        String fullResponse = response.convertToString();
        assertEquals("HTTP/1.1 200 OK\r\n", fullResponse);
    }

    @Test
    public void setsLocationHeader() throws Exception {
        request = builder.build("GET /redirect HTTP/1.1");
        response = dispatcher.dispatch(request);
        String fullResponse = response.convertToString();
        assertEquals("HTTP/1.1 307 Moved Temporarily\r\nLocation: http://localhost:5000/\r\n\n", fullResponse);
    }

}