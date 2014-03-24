import org.junit.Before;
import org.junit.Test;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

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

    @Test
    public void returnsStringRepresentationOfParams() {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("Foo", "Bar");
        params.put("Baz", "Snazz");
        request.params = params;
        String stringParams = request.convertParamsToString();
        assertEquals("Foo = Bar\nBaz = Snazz\n", stringParams);
    }
}
