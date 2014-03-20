import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class ResponseTest {
    private Request request;
    private Dispatcher dispatcher = new Dispatcher();
    private Response response;

    @Test
    public void returnsResponseWithFileContentsAndStatusCode() throws Exception {
        request = new Request("GET /file1 HTTP/1.0");
        response = dispatcher.dispatch(request);
        String fullResponse = response.convertToString();
        assertEquals("HTTP/1.0 200 OK\r\nContent-Type: text/plain\r\n\nfile1 contents", fullResponse);
    }

    @Test
    public void returns404ResponseWhenNoFileLocated() throws Exception {
        request = new Request("GET /foobar HTTP/1.0");
        response = dispatcher.dispatch(request);
        String fullResponse = response.convertToString();
        assertEquals("HTTP/1.0 404 Not Found\r\nContent-Type: text/plain\r\n\n", fullResponse);
    }

    @Test
    public void returnsCorrectStatusMessageFor200StatusCode() throws Exception {
        request = new Request("GET /file1 HTTP/1.0");
        response = dispatcher.dispatch(request);
        String statusMessage = response.getStatusMessage();
        assertEquals("OK", statusMessage);
    }

    @Test
    public void returns200OnPostRequest() throws Exception {
        request = new Request("POST /form HTTP/1.1");
        response = dispatcher.dispatch(request);
        String fullResponse = response.convertToString();
        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\n", fullResponse);
    }

    @Test public void setsLocationHeader() throws Exception {
        request = new Request("GET /redirect HTTP/1.1");
        response = dispatcher.dispatch(request);
        String fullResponse = response.convertToString();
        assertEquals("HTTP/1.1 301 Moved Permanently\r\nLocation: /\nContent-Type: text/plain\r\n\n", fullResponse);
    }

}