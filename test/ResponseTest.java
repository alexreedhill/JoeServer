import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static junit.framework.Assert.*;

/**
 * Created by alexhill on 3/17/14.
 */
public class ResponseTest {
    private Request request;

    @Before
    public void setUpRequest() {
        request = new Request("GET /file1 HTTP/1.0");
    }

    @Test
    public void returnsContentsOfFile() throws IOException {
        Response response = new Response(request);
        String fileContents = response.getFileContents();
        assertEquals("file1 contents", fileContents);
    }

    @Test
    public void returnsResponseWithFileContentsAndStatusCode() throws IOException {
        Response response = new Response(request);
        String fullResponse = response.returnFullResponse();
        assertEquals("HTTP/1.0 200 OK\n\nfile1 contents\r\n", fullResponse);
    }
}