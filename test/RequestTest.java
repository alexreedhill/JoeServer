import org.junit.Test;
import static junit.framework.Assert.*;


/**
 * Created by alexhill on 3/17/14.
 */
public class RequestTest {
    @Test
    public void getMethod() {
        Request request = new Request("GET /path/to/file.html HTTP/1.0");
        assertEquals("GET", request.method);
    }

    @Test
    public void getPath() {
        Request request = new Request("GET /path/to/file.html HTTP/1.0");
        assertEquals("/path/to/file.html", request.path);
    }

    @Test
    public void getHttpVersion() {
        Request request = new Request("GET /path/to/file.html HTTP/1.0");
        assertEquals("HTTP/1.0", request.httpVersion);
    }
}
