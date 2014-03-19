import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class ResponseTest {
    private Request request;
    private Response response;

    @Test
    public void returnsResponseWithFileContentsAndStatusCode() throws Exception {
        request = new Request("GET /file1 HTTP/1.0");
        response = new Response(request);
        byte[] responseBytes = response.buildFullResponse();
        String fullResponse = new String(responseBytes, "UTF-8");
        assertEquals("HTTP/1.0 200 OK\r\nContent-Type: text/plain\r\n\nfile1 contents", fullResponse);
    }

    @Test
    public void returns404ResponseWhenNoFileLocated() throws Exception {
        request = new Request("GET /foobar HTTP/1.0");
        response = new Response(request);
        byte[] responseBytes = response.respond();
        String fullResponse = new String(responseBytes, "UTF-8");
        assertEquals("HTTP/1.0 404 Not Found\r\n", fullResponse);
    }

}