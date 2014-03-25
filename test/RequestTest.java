import org.junit.Before;
import org.junit.Test;
import sun.reflect.annotation.ExceptionProxy;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class RequestTest{
    private Request request;
    private RequestBuilder builder;

    @Before
    public void setupRequestBuilder() {
        builder = new RequestBuilder();
    }

    @Test
    public void getMethod() throws Exception {
        request = builder.build("GET /path/to/file.html HTTP/1.0");
        assertEquals("GET", request.method);
    }

    @Test
    public void getPath() throws Exception {
        request = builder.build("GET /path/to/file.html HTTP/1.0");
        assertEquals("/path/to/file.html", request.path);
    }

    @Test
    public void getHttpVersion() throws Exception {
        request = builder.build("GET /path/to/file.html HTTP/1.0");
        assertEquals("HTTP/1.0", request.httpVersion);
    }

    @Test
    public void returnsStringRepresentationOfParams() throws Exception {
        request = builder.build("GET /path/to/file.html?Foo=Bar&Baz=Snazz HTTP/1.0");
        assertEquals("Foo = Bar\nBaz = Snazz\n", request.paramsString);
    }

    @Test
    public void returnsHeaders() throws Exception {
        request = builder.build("GET /path/to/file.html HTTP/1.1\r\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        assertEquals(request.headers.get("Authorization"), "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
    }

    @Test
    public void splitsPathFromParams() throws Exception {
        request = builder.build("GET /parameters?foo=bar HTTP/1.1\r\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        assertEquals("/parameters", request.path);
        assertEquals("bar", request.params.get("foo") );
    }
}
