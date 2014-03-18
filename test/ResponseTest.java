import mocks.MockClient;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;

import static junit.framework.Assert.*;

/**
 * Created by alexhill on 3/17/14.
 */
public class ResponseTest {
    private Request request;
    private Response response;
    private PipedOutputStream out = new PipedOutputStream();

    @Test
    public void returnsResponseWithFileContentsAndStatusCode() throws Exception {
        request = new Request("GET /file1 HTTP/1.0");
        response = new Response(request);
        String fullResponse = response.respond(out);
        assertEquals("HTTP/1.0 200 OK\n\nfile1 contents\r\n", fullResponse);
    }

    @Test
    public void returns404WhenNoFileLocated() throws Exception {
        request = new Request("GET /foobar HTTP/1.0");
        response = new Response(request);
        response.respond(out);
        String statusCode = response.getStatusCode();
        assertEquals("404", statusCode);
    }

    @Test public void returns200StatusCodeForImage() throws Exception {
        request = new Request("GET /image.jpeg HTTP/1.0");
        response = new Response(request);
        response.respond(out);
        String statusCode = response.getStatusCode();
        assertEquals("200", statusCode);
    }
}