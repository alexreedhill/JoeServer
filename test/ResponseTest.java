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

    @Test
    public void returnsResponseWithFileContentsAndStatusCode() throws Exception {
        request = new Request("GET /file1 HTTP/1.0");
        response = new Response(request);
        byte[] responseBytes = response.buildFullResponse();
        String fullResponse = new String(responseBytes, "UTF-8");
        assertEquals("HTTP/1.0 200 OK\r\nContent-Type: text/plain\r\n\nfile1 contents", fullResponse);
    }

    @Test
    public void returns404WhenNoFileLocated() throws Exception {
        request = new Request("GET /foobar HTTP/1.0");
        response = new Response(request);
        String statusCode = response.statusCode;
        assertEquals("404", statusCode);
    }
}