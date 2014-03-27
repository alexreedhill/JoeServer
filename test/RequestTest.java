import org.junit.Before;
import org.junit.Test;
import sun.reflect.annotation.ExceptionProxy;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class RequestTest{
    private Request request;
    private RequestBuilder builder;

    @Test
    public void getMethod() throws Exception {
        builder = new RequestBuilder("GET /path/to/file.html HTTP/1.0");
        request = builder.build();
        assertEquals("GET", request.method);
    }

    @Test
    public void getPath() throws Exception {
        builder = new RequestBuilder("GET /path/to/file.html HTTP/1.0");
        request = builder.build();
        assertEquals("/path/to/file.html", request.path);
    }

    @Test
    public void getHttpVersion() throws Exception {
        builder = new RequestBuilder("GET /path/to/file.html HTTP/1.0");
        request = builder.build();
        assertEquals("HTTP/1.0", request.httpVersion);
    }

    @Test
    public void returnsStringRepresentationOfParams() throws Exception {
        builder = new RequestBuilder("GET /path/to/file.html?Foo=Bar&Baz=Snazz HTTP/1.0");
        request = builder.build();
        assertEquals("Foo = Bar\nBaz = Snazz\n", request.params);
    }

    @Test
    public void returnsHeaders() throws Exception {
        builder = new RequestBuilder("GET /path/to/file.html HTTP/1.1\r\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        request = builder.build();
        assertEquals(request.headers.get("Authorization"), "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
    }

    @Test
    public void splitsPathFromParams() throws Exception {
        builder = new RequestBuilder("GET /parameters?foo=bar HTTP/1.1\r\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        request = builder.build();
        assertEquals("/parameters", request.path);
        assertEquals("bar", request.paramsHash.get("foo") );
    }
}
