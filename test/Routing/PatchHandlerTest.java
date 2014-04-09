import Mocks.MockFileReader;
import Mocks.MockFileWriter;
import Request.Request;
import Request.RequestBuilder;
import Response.Response;
import Routing.PatchHandler;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PatchHandlerTest {
    private Response response;
    private Request request;
    private RequestBuilder builder;
    private MockFileReader mockFileReader;
    private MockFileWriter mockFileWriter;

    public void setupNoIfMatchRequest() throws Exception{
        builder = new RequestBuilder("PATCH /patch-content.txt HTTP/1.1\r\n" +
                "Content-Length: 7\r\n\n" +
                "patched");
    }

    public void handleRequest() throws Exception {
        request = builder.build();
        PatchHandler handler = new PatchHandler(request);
        response = handler.handle();
    }

    public void setupMocks() {
        mockFileReader = new MockFileReader();
        mockFileWriter = new MockFileWriter(request);
    }

    @Test
    public void returns412WithNoIfMatch() throws Exception {
        setupNoIfMatchRequest();
        handleRequest();
        assertEquals("412", response.statusCode);
    }

    @Test
    public void setsETagOnResponseIfRequestIfMatchMatchesResourceLongerNewBody() throws Exception {
        builder = new RequestBuilder("PATCH /patch-content.txt HTTP/1.1\r\n" +
                "If-Match: 40bd001563085fc35165329ea1ff5c5ecbdbbeef\n" +
                "Content-Length: 7\r\n\n" +
                "patched");
        request = builder.build();
        setupMocks();
        PatchHandler handler = new PatchHandler(request, mockFileReader, mockFileWriter);
        response = handler.handle();
        assertEquals(DigestUtils.sha1Hex("patched"), response.getHeaderValue("ETag"));
    }

    @Test
    public void setsETagOnResponseIfRequestIfMatchMatchesResourceShorterNewBodyButResultsInSameBody() throws Exception {
        builder = new RequestBuilder("PATCH /patch-content.txt HTTP/1.1\r\n" +
                "If-Match: 40bd001563085fc35165329ea1ff5c5ecbdbbeef\n" +
                "Content-Length: 2\r\n\n" +
                "12");
        request = builder.build();
        setupMocks();
        PatchHandler handler = new PatchHandler(request, mockFileReader, mockFileWriter);
        response = handler.handle();
        assertEquals(DigestUtils.sha1Hex("123"), response.getHeaderValue("ETag"));
    }

    @Test
    public void setsTtagOnResponseIfRequestIfMatchMatchesResourceShorterNewBodyButResultsInDifferentBody() throws Exception {
        builder = new RequestBuilder("PATCH /patch-content.txt HTTP/1.1\r\n" +
                "If-Match: 40bd001563085fc35165329ea1ff5c5ecbdbbeef\n" +
                "Content-Length: 2\r\n\n" +
                "13");
        request = builder.build();
        setupMocks();
        PatchHandler handler = new PatchHandler(request, mockFileReader, mockFileWriter);
        response = handler.handle();
        assertEquals(DigestUtils.sha1Hex("133"), response.getHeaderValue("ETag"));
    }
}
