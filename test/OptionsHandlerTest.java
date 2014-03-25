import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OptionsHandlerTest {
    private OptionsHandler optionsHandler;

    @Before
    public void setUpRequest() throws Exception {
        RequestBuilder builder = new RequestBuilder();
        Request request = builder.build("OPTIONS /method_options HTTP/1.0");
        optionsHandler = new OptionsHandler(request);
    }

    @Test
    public void setsAllowHeader() throws Exception {
        Response response = optionsHandler.handle();
        String allowHeader = response.getHeaderValue("Allow");
        assertEquals("GET,HEAD,POST,OPTIONS,PUT", allowHeader);
    }
}
