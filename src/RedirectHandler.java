import java.io.IOException;

/**
 * Created by alexhill on 3/19/14.
 */
public class RedirectHandler {
    private Response response;
    private Request redirect;

    public Response handle(Request request) throws Exception {
        response = new Response(request);
        response.set301Response();
        return response;
    }
}
