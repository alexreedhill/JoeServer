import org.junit.Before;
import org.junit.Test;
import sun.reflect.annotation.ExceptionProxy;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class RequestTest{
    private Request request;


    @Test
    public void getMethod() throws Exception {
        request = new Request("GET /path/to/file.html HTTP/1.0");
        assertEquals("GET", request.method);
    }

    @Test
    public void getPath() throws Exception {
        request = new Request("GET /path/to/file.html HTTP/1.0");
        assertEquals("/path/to/file.html", request.path);
    }

    @Test
    public void getHttpVersion() throws Exception {
        request = new Request("GET /path/to/file.html HTTP/1.0");
        assertEquals("HTTP/1.0", request.httpVersion);
    }

    @Test
    public void returnsStringRepresentationOfParams() throws Exception {
        request = new Request("GET /path/to/file.html HTTP/1.0");
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("Foo", "Bar");
        params.put("Baz", "Snazz");
        request.params = params;
        String stringParams = request.convertParamsToString();
        assertEquals("Foo = Bar\nBaz = Snazz\n", stringParams);
    }

    @Test
    public void returnsHeaders() throws Exception {
        request = new Request("GET /path/to/file.html HTTP/1.1\r\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        assertEquals(request.headers.get("Authorization"), "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
    }

    @Test
    public void splitsPathFromParams() throws Exception {
        request = new Request("GET /parameters?foo=bar HTTP/1.1\r\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        assertEquals("/parameters", request.path);
        assertEquals("bar", request.params.get("foo") );
    }
}
