import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by alexhill on 3/20/14.
 */
public class OptionsHandlerTest {
    private Request request;
    private OptionsHandler optionsHandler = new OptionsHandler();
    private Response response;

    @Test
    public void setsAllowHeader() throws Exception {
        request = new Request("OPTIONS /method_options HTTP/1.0");
        response = optionsHandler.handle(request);
        String allowHeader = response.getHeaderValue("Allow");
        assertEquals("GET,HEAD,POST,OPTIONS,PUT", allowHeader);
    }
}
