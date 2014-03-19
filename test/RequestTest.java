import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;


/**
 * Created by alexhill on 3/17/14.
 */
public class RequestTest{
    private Request request;

    @Before
    public void initializeRequest() throws Exception {
        request = new Request("GET /path/to/file.html HTTP/1.0");
    }

    @Test
    public void getMethod() {
        assertEquals("GET", request.method);
    }

    @Test
    public void getPath() {
        assertEquals("/path/to/file.html", request.path);
    }

    @Test
    public void getHttpVersion() {
        assertEquals("HTTP/1.0", request.httpVersion);
    }
}
