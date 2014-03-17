import org.junit.Test;
import java.io.IOException;
import static junit.framework.Assert.*;

/**
 * Created by alexhill on 3/17/14.
 */
public class ResponseTest {

    @Test
    public void returnsContentsOfFile() throws IOException {
        Response response = new Response("GET /file1 HTTP/1.0");
        String fileContents = response.getFileContents();
        assertEquals("file1 contents", fileContents);
    }

    @Test
    public void returnsResponseWithFileContentsAndStatusCode() throws IOException {
        Response response = new Response("GET /file1 HTTP/1.0");
        String fullResponse = response.returnFullResponse();
        assertEquals("HTTP/1.0 200 OK\n\nfile1 contents\r\n", fullResponse);
    }
}