import Routing.OptionsHandler;
import Request.Request;
import Request.RequestBuilder;
import Response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OptionsHandlerTest {
    private OptionsHandler optionsHandler;

    @Before
    public void setUpRequest() throws Exception {
        String publicPath = "../cob_spec/public/";
        RequestBuilder builder = new RequestBuilder("OPTIONS /method_options HTTP/1.0", publicPath);
        Request request = builder.build();
        optionsHandler = new OptionsHandler(request);
    }

    @Test
    public void setsAllowHeader() throws Exception {
        Response response = optionsHandler.handle();
        String allowHeader = response.getHeaderValue("Allow");
        assertEquals("GET,HEAD,POST,OPTIONS,PUT", allowHeader);
    }
}
