import java.io.IOException;

public class PostHandler implements RequestHandler {
    private Response response;

    public Response handle(Request request) throws IOException {
        response = new Response(request);
        response.set200Response();
        return response;
    }
}
