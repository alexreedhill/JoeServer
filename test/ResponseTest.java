import mocks.MockClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.OutputStream;

import static junit.framework.Assert.*;

/**
 * Created by alexhill on 3/17/14.
 */
public class ResponseTest {
    private Request request;
    private Response response;
    private OutputStream out;


    @Before
    public void setupMockClient() throws IOException {
        MockClient client = new MockClient();
        out = client.getOutputStream();
    }

    @After
    public void closeOutputStream() throws IOException {
        out.close();
    }


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
}