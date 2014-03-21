import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

public class GetHandlerTest {
    private Request request;

    @Before
    public void setUpRequest() throws Exception {
        request = new Request("GET /logs HTTP/1.1");
    }

    @Test
    public void recognizesRestrictedRoute() throws Exception {
        GetHandler handler = new GetHandler(request);
        Boolean result = handler.restrictedRoute();
        assertTrue(result);
    }

}
