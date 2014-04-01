import Mocks.MockFileReader;
import Request.Request;
import Request.RequestBuilder;
import Response.Response;
import Routing.PatchHandler;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PatchHandlerTest {
    private Response response;
    private RequestBuilder builder;

    public void setupNoIfMatchRequest() throws Exception{
        builder = new RequestBuilder("PATCH /patch-content.txt HTTP/1.1\r\n" +
                "Content-Length: 7\r\n\n" +
                "patched");
    }

    public void handleRequest() throws Exception {
        Request request = builder.build();
        PatchHandler handler = new PatchHandler(request);
        response = handler.handle();
    }

    @Test
    public void returnsCorrectStatus() throws Exception {
        setupNoIfMatchRequest();
        handleRequest();
        assertEquals("412", response.statusCode);
    }

    @Test
    public void updatesFileIfRequestETagMatchesResource() throws Exception {
        builder = new RequestBuilder("PATCH /patch-content.txt HTTP/1.1\r\n" +
                "If-Match: 40bd001563085fc35165329ea1ff5c5ecbdbbeef\n" +
                "Content-Length: 7\r\n\n" +
                "patched");
        Request request = builder.build();
        MockFileReader fileReader = new MockFileReader();
        PatchHandler handler = new PatchHandler(request, fileReader);
        response = handler.handle();
        assertEquals(DigestUtils.sha1Hex("patched"), response.getHeaderValue("ETag"));
    }
}
