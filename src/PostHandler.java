import java.io.IOException;

/**
 * Created by alexhill on 3/19/14.
 */
public class PostHandler implements RequestHandler {
    private Response response;

    public Response handle(Request request) throws IOException {
        response = new Response(request);
        response.set200Response();
        return response;
    }
}
